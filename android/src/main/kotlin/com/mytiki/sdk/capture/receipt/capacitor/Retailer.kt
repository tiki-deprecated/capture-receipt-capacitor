/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
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
import timber.log.Timber


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
    fun login( call: PluginCall, context: Context ) {
        val req = ReqRetailerLogin(call.data)
        val account = Account(
            RetailerEnum.fromString(req.retailer).toInt(),
            PasswordCredentials(req.username, req.password)
        )
        client.link(account)
            .addOnSuccessListener {
                if (it) {
                    verify(account, true, context, call)
                } else {
                    call.reject("login failed")
                }
            }
            .addOnFailureListener {
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
            } else {
                call.reject("Account not found")
            }
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun flush(call: PluginCall){
        client.resetHistory().addOnSuccessListener {
            call.resolve()
        }.addOnFailureListener {
            call.reject(it.message)
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
                        { a: Int, results: ScanResults?, b: Int, c: String ->
                            if (results != null) {
                                val rsp = RspRetailerOrders(
                                    RetailerEnum.fromInt(retailer).toString(),
                                    username, results
                                )
                                Log.e("TIKI", rsp.toJson().toString())
                                call.resolve(JSObject(rsp.toJson().toString()))
                            } else {
                                Log.e("TIKI", "NO RESULT")
                                call.reject("no result")
                            }
                        }
                    val ordersFailureCallback = { _: Int, exception: AccountLinkingException ->
                        Log.e("TIKI", exception.message ?: "exception wihtout message")
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
                               verify(account).await()
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
    private fun verify( account: Account, showDialog: Boolean = false, context: Context? = null, call: PluginCall? = null ): CompletableDeferred<Boolean>{
        val verifyCompletable = CompletableDeferred<Boolean>()
        client.verify(
            account.retailerId,
            success = { isVerified: Boolean, _: String ->
                if(isVerified){
                    val rsp = RspRetailerAccount(account, true)
                    call?.resolve(JSObject.fromJSONObject(rsp.toJson()))
                    verifyCompletable.complete(true)
                }else {
                    client.unlink(account)
                    call?.reject("login failed")
                    verifyCompletable.complete(false)
                }
            },
            failure = { exception ->
                if(call == null){
                    verifyCompletable.complete(false)
                }else if (exception.code == VERIFICATION_NEEDED && exception.view != null && context != null) {
                    exception.view!!.isFocusableInTouchMode = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        exception.view!!.focusable = View.FOCUSABLE
                    }
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle("Verify your account")
                    builder.setView(exception.view)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    // TODO refazer a verificação dps da webview dismiss
                }else{
                    when (exception.code){
                        INTERNAL_ERROR -> call.reject("Login failed: Internal Error")
                        INVALID_CREDENTIALS -> call.reject("Login failed: Invalid Credentials")
                        PARSING_FAILURE -> call.reject("Login failed: Parsing Failure")
                        USER_INPUT_COMPLETED -> call.reject("Login failed: User Input Completed")
                        JS_CORE_LOAD_FAILURE -> call.reject("Login failed: JS Core Load Failure")
                        JS_INVALID_DATA -> call.reject("Login failed: JS Invalid Data")
                        MISSING_CREDENTIALS -> call.reject("Login failed: Missing Credentials")
                        else -> call.reject("Login failed: Unknown Error")
                    }
                    client.unlink(account)
                }
            }
        )
        return verifyCompletable
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun client(
        context: Context,
        dayCutoff: Int = 500,
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
