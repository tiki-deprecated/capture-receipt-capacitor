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
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspScan
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
    fun login( call: PluginCall, account: Account, context: Context ) {
        val mbAccount = com.microblink.linking.Account(
            RetailerEnum.fromString(account.accountCommon.source).toValue(),
            PasswordCredentials(account.username, account.password!!)
        )
        client.link(mbAccount)
            .addOnSuccessListener {
                if (it) {
                    verify(mbAccount, true, context, call)
                } else {
                    call.reject("login failed")
                }
            }
            .addOnFailureListener {
                call.reject(it.message)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun remove(call: PluginCall, accountCommon: AccountCommon){
        client.accounts().addOnSuccessListener { accounts ->
            val mbAccount = accounts?.firstOrNull {
                it.retailerId == RetailerEnum.fromString(accountCommon.source).toValue()
            }
            if (mbAccount != null) {
                client.unlink(mbAccount).addOnSuccessListener {
                    call.resolve()
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
            val accounts = accounts().await()
            accounts.forEach {
                if(it.isVerified!!) {
                    val retailer = RetailerEnum.fromString(it.accountCommon.source).toValue()
                    val ordersSuccessCallback =
                        { _: Int, results: ScanResults?, remaining: Int, _: String ->
                            if (results != null) {
                                if(remaining == 0) {
                                    val rsp = RspScan(results, it, false)
                                    call.resolve(JSObject(rsp.toJson().toString()))
                                }else{
                                    val rsp = RspScan(results, it)
                                    call.resolve(JSObject(rsp.toJson().toString()))
                                }


                            } else {
                                call.reject("no result")
                            }
                        }
                    val ordersFailureCallback = { _: Int, exception: AccountLinkingException ->
                        Log.e("TIKI", exception.message ?: "exception wihtout message")
                        call.reject(exception.message)
                    }
                    client.orders(
                        retailer,
                        ordersSuccessCallback,
                        ordersFailureCallback,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders( call: PluginCall, account: Account ) {
        MainScope().async {
            val mbAccount = mbAccounts().await().first{
                account.username === it.credentials.username() && account.accountCommon.source === RetailerEnum.fromValue(it.retailerId).name
            }
            account.isVerified = verify(mbAccount).await()
            if(account.isVerified!!) {
                val retailer = RetailerEnum.fromString(account.accountCommon.source).toValue()
                val ordersSuccessCallback =
                    { _: Int, results: ScanResults?, _: Int, _: String ->
                        if (results != null) {
                            val rsp = RspScan(results, account,false)
                            call.resolve(JSObject(rsp.toJson().toString()))
                        } else {
                            call.reject("no result")
                        }
                    }
                val ordersFailureCallback = { _: Int, exception: AccountLinkingException ->
                    Log.e("TIKI", exception.message ?: "exception wihtout message")
                    call.reject(exception.message)
                }
                client.orders(
                    retailer,
                    ordersSuccessCallback,
                    ordersFailureCallback,
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun accounts(): CompletableDeferred<List<Account>> {
        val accounts = CompletableDeferred<List<Account>>()
        val list = mutableListOf<Account>()
        MainScope().async{
            mbAccounts().await().map{mbAccount ->
                val account = Account.fromRetailerAccount(mbAccount)
                account.isVerified = verify(mbAccount).await()
                list.add(account)
            }
            accounts.complete(list)
        }
        return accounts
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun mbAccounts(): CompletableDeferred<List<com.microblink.linking.Account>> {
        val mbAccounts = CompletableDeferred<List<com.microblink.linking.Account>>()
        client.accounts()
            .addOnSuccessListener { mbAccountList ->
                MainScope().async {
                    if (mbAccountList != null) {
                        mbAccounts.complete(mbAccountList)
                    } else {
                        mbAccounts.complete(mutableListOf())
                    }
                }
            }
            .addOnFailureListener {
                mbAccounts.completeExceptionally(it)
            }
        return mbAccounts
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun verify( mbAccount: com.microblink.linking.Account, showDialog: Boolean = false, context: Context? = null, call: PluginCall? = null ): CompletableDeferred<Boolean>{
        val verifyCompletable = CompletableDeferred<Boolean>()
        val account = Account.fromRetailerAccount(mbAccount)
        client.verify(
            RetailerEnum.fromString(account.accountCommon.source).value,
            success = { isVerified: Boolean, _: String ->
                if(isVerified){
                    account.isVerified = true
                    call?.resolve(account.toRsp())
                    verifyCompletable.complete(true)
                }else {
                    client.unlink(mbAccount)
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
                    client.unlink(mbAccount)
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
