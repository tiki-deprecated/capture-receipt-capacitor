/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.core.ScanResults
import com.microblink.linking.*
import com.mytiki.sdk.capture.receipt.capacitor.components.CustomAlertDialog
import com.mytiki.sdk.capture.receipt.capacitor.components.CustomAlertDialog.ViewDestroyedListener
import com.mytiki.sdk.capture.receipt.capacitor.components.MyRendererTrackingWebViewClient
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

    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isLinkInitialized = CompletableDeferred<Unit>()
        BlinkReceiptLinkingSdk.licenseKey = req.licenseKey
        BlinkReceiptLinkingSdk.productIntelligenceKey = req.productKey
        BlinkReceiptLinkingSdk.initialize(context, OnInitialize(isLinkInitialized, onError))
        return isLinkInitialized
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun login( call: PluginCall, context: Context ) {
        val client: AccountLinkingClient = client(context)
        val req = ReqRetailerLogin(call.data)
        val account = Account(
            RetailerEnum.fromString(req.retailer).toInt(),
            PasswordCredentials(req.username, req.password)
        )
        client.link(account).addOnSuccessListener {
                if (it) {
                    MainScope().async {
                        verify(account, context,true,  call).await()
                        client.close()
                    }
                } else {
                    call.reject("login failed")
                    client.close()
                }
            }.addOnFailureListener {
                call.reject(it.message)
                client.close()
            }
    }

    fun accounts(call: PluginCall, context: Context){
        MainScope().async{
            try {
                val allAccounts = getAccounts(context).await()
                val rsp = RspRetailerAccountList(allAccounts.toMutableList())
                call.resolve(JSObject.fromJSONObject(rsp.toJson()))
            }catch(e: Exception){
                call.reject(e.message)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun remove(call: PluginCall, context: Context){
        val client: AccountLinkingClient = client(context)
        val req = ReqRetailerLogin(call.data)
        client.accounts().addOnSuccessListener { accounts ->
            val reqAccount = accounts?.firstOrNull {
                it.retailerId == RetailerEnum.fromString(req.retailer).toInt()
            }
            if (reqAccount != null) {
                client.unlink(reqAccount).addOnSuccessListener {
                    val rsp = RspRetailerAccount(reqAccount, false)
                    call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                    client.close()
                }.addOnFailureListener {
                    call.reject(it.message)
                    client.close()
                }
            } else {
                call.reject("Account not found")
                client.close()
            }
        }.addOnFailureListener{
            call.reject(it.message)
            client.close()
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun flush(call: PluginCall, context: Context){
        val client: AccountLinkingClient = client(context)
        client.resetHistory().addOnSuccessListener {
            call.resolve()
            client.close()
        }.addOnFailureListener {
            call.reject(it.message)
            client.close()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders( call: PluginCall, context: Context) {
        val client: AccountLinkingClient = client(context)

        MainScope().async {
            val accounts = getAccounts(context).await()
            accounts.forEach {
                if(it.isVerified) {
                    val retailer = it.account.retailerId
                    val username = it.account.credentials.username()
                    val ordersSuccessCallback =
                        { _: Int, results: ScanResults?, remaining: Int, _: String ->
                            if (results != null) {
                                val rsp = RspRetailerOrders(
                                    RetailerEnum.fromInt(retailer).toString(),
                                    username, results
                                )
                                call.resolve(JSObject(rsp.toJson().toString()))
                            } else {
                                call.reject("no result")
                            }
                            if (remaining == 0){
                                client.close()
                            }
                        }
                    val ordersFailureCallback = { _: Int, exception: AccountLinkingException ->
                        call.reject(exception.message)
                        client.close()
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
    private fun getAccounts(context: Context): CompletableDeferred<List<RspRetailerAccount>> {
        val client: AccountLinkingClient = client(context)

        val getAccounts = CompletableDeferred<List<RspRetailerAccount>>()
        client.accounts()
            .addOnSuccessListener { accounts ->
                MainScope().async {
                    if (accounts != null) {
                        val accountList = accounts.map{ account ->
                            RspRetailerAccount(
                                account,
                               verify(account, context).await()
                            )
                        }
                        getAccounts.complete(accountList)
                        client.close()
                    } else {
                        getAccounts.complete(mutableListOf())
                        client.close()
                    }
                }
            }
            .addOnFailureListener {
                getAccounts.completeExceptionally(it)
                client.close()
            }
        return getAccounts
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun verify( account: Account, context: Context, showDialog: Boolean = false,  call: PluginCall? = null ): CompletableDeferred<Boolean>{
        val client: AccountLinkingClient = client(context)

        val verifyCompletable = CompletableDeferred<Boolean>()
        client.verify(
            account.retailerId,
            success = { isVerified: Boolean, _: String ->
                if(isVerified){
                    val rsp = RspRetailerAccount(account, true)
                    call?.resolve(JSObject.fromJSONObject(rsp.toJson()))
                    verifyCompletable.complete(true)
                    client.close()
                }else {
                    client.unlink(account)
                    call?.reject("login failed")
                    verifyCompletable.complete(false)
                    client.close()
                }
            },
            failure = { exception ->
                if(call == null){
                    verifyCompletable.complete(false)
                    client.close()
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
                    client.close()
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
