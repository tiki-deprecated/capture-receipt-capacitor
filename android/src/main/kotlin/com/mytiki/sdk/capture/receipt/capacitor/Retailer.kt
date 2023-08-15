/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.app.Dialog
import android.content.Context
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.core.ScanResults
import com.microblink.linking.*
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqRetailerLogin
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerAccountList
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerOrders
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async


class Retailer {
    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var client: AccountLinkingClient

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isLinkInitialized = CompletableDeferred<Unit>()
        BlinkReceiptLinkingSdk.licenseKey = req.licenseKey
        BlinkReceiptLinkingSdk.productIntelligenceKey = req.productKey
        BlinkReceiptLinkingSdk.initialize(context, OnInitialize(isLinkInitialized, onError))
        client = client(context)
        return isLinkInitialized
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun login( call: PluginCall, context: Context ){
        val req = ReqRetailerLogin(call.data)
        val account = Account(
            RetailerEnum.fromString(req.retailer).toInt(),
            PasswordCredentials(req.username, req.password)
        )
        client.link(account).addOnSuccessListener {
            MainScope().async {
                val isVerified = verify(account.retailerId, true, context).await()
                val rsp = RspRetailerAccount(account, isVerified)
                call.resolve(JSObject.fromJSONObject(rsp.toJson()))
            }
        }.addOnFailureListener {
            call.reject(it.message)
        }
    }

    fun accounts(call: PluginCall){
        MainScope().async{
            try {
                val allAccounts = getAccounts().await()
                val rsp = RspRetailerAccountList(allAccounts.toMutableList())
                call.resolve(JSObject.fromJSONObject(rsp.toJson()))
            }catch(e: Exception){
                call.reject(e.message)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun remove(call: PluginCall){
        val req = ReqRetailerLogin(call.data)
        client.accounts().addOnSuccessListener { accounts ->
            val reqAccount = accounts?.firstOrNull {
                it.retailerId == RetailerEnum.fromString(req.retailer).toInt()
            }
            if (reqAccount != null) {
                client.unlink(reqAccount).addOnSuccessListener {
                    val rsp = RspRetailerAccount(reqAccount, false)
                    call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                }.addOnFailureListener {
                    call.reject(it.message)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders( call: PluginCall ) {
        MainScope().async {
            val accounts = getAccounts().await()
            accounts.forEach {
                if(it.isVerified) {
                    val retailer = it.account.retailerId
                    val username = it.account.credentials.username()
                    val ordersSuccessCallback =
                        { _: Int, results: ScanResults?, _: Int, _: String ->
                            if (results != null) {
                                val rsp = RspRetailerOrders(
                                    RetailerEnum.fromInt(retailer).toString(),
                                    username, results
                                )
                                call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                            } else {
                                call.reject("no orders")
                            }
                        }
                    val ordersFailureCallback = { _: Int, exception: AccountLinkingException ->
                        call.reject(exception.message)
                    }
                    client.orders(
                        it.account.retailerId,
                        ordersSuccessCallback,
                        ordersFailureCallback,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getAccounts(): CompletableDeferred<List<RspRetailerAccount>> {
        val getAccounts = CompletableDeferred<List<RspRetailerAccount>>()
        client.accounts()
            .addOnSuccessListener { accounts ->
                MainScope().async {
                    if (accounts != null) {
                        val accountList = accounts.map{ account ->
                            RspRetailerAccount(
                                account,
                                verify(account.retailerId).await()
                            )
                        }
                        getAccounts.complete(accountList)
                    } else {
                        getAccounts.complete(mutableListOf())
                    }
                }
            }
            .addOnFailureListener {
                getAccounts.completeExceptionally(it)
            }
        return getAccounts
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun verify( retailerId: Int, showDialog: Boolean = false, context: Context? = null ): CompletableDeferred<Boolean>{
        val verifyCompletable = CompletableDeferred<Boolean>()
        client.verify(
            retailerId,
            { isVerified: Boolean, _: String ->
                verifyCompletable.complete(isVerified)
            },{ exception ->
                if( showDialog &&
                    exception.code == VERIFICATION_NEEDED &&
                    exception.view != null && context != null)
                {
                    val dialog = Dialog(context)
                    dialog.setContentView(exception.view!!)
                    dialog.setOnDismissListener {
                        MainScope().async {
                            verifyCompletable.complete(verify(retailerId, false).await())
                        }
                    }
                } else {
                    verifyCompletable.complete(false)
                }
            }
        )
        return verifyCompletable
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun client(
        context: Context,
        dayCutoff: Int = 15,
        latestOrdersOnly: Boolean = false,
        countryCode: String = "US",
    ): AccountLinkingClient{
        val client = AccountLinkingClient(context)
        client.dayCutoff = dayCutoff
        client.latestOrdersOnly = latestOrdersOnly
        client.countryCode = countryCode

        return client
    }
}
