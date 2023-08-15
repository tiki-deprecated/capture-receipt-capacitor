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
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqRetailerAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerAccountList
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspRetailerOrders
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.json.JSONArray
import org.json.JSONObject


class Retailer {
    private lateinit var client: AccountLinkingClient

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isLinkInitialized = CompletableDeferred<Unit>()
        BlinkReceiptLinkingSdk.licenseKey = req.licenseKey!!
        BlinkReceiptLinkingSdk.productIntelligenceKey = req.productKey!!
        BlinkReceiptLinkingSdk.initialize(context, OnInitialize(isLinkInitialized, onError))
        client = client(context)
        return isLinkInitialized
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun client(
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun account(
        call: PluginCall
    ){
        val req = ReqRetailerAccount(call.data)
        val account = Account(
            req.retailerId.value,
            PasswordCredentials(req.username, req.password)
        )
        client.link(account).addOnSuccessListener {
            val rsp = RspRetailerAccount(account)
            call.resolve(JSObject.fromJSONObject(rsp.toJson().put("isAccountLinked", it)))
        }.addOnFailureListener {
            call.reject(it.message)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun accounts(call: PluginCall){
        client.accounts().addOnSuccessListener {allAccounts ->
            if(allAccounts != null) {
                val rsp = RspRetailerAccountList(allAccounts)
                call.resolve(JSObject.fromJSONObject(rsp.toJson().put("isAccounts", true)))
            }else{
                call.resolve(
                    JSObject.fromJSONObject(JSONObject()
                        .put("accounts", JSONArray())
                        .put("isAccounts", true)
                    )
                )
            }
        }.addOnFailureListener {
            call.reject(it.message)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun remove(call: PluginCall){
        val req = ReqRetailerAccount(call.data)
        client.accounts().addOnSuccessListener { accounts ->
            val reqAccount = accounts?.firstOrNull { it.retailerId == req.retailerId.value }
            if (reqAccount != null) {
                client.unlink(reqAccount).addOnSuccessListener {
                    val rsp = RspRetailerAccount(reqAccount)
                    call.resolve(JSObject.fromJSONObject(rsp.toJson().put("isAccountRemoved", it)))
                }.addOnFailureListener {
                    call.reject(it.message)
                }
            } else {
                call.resolve(JSObject.fromJSONObject(JSONObject().put("isAccountRemoved", true)))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun clientVerification(
        context: Context,
        call: PluginCall,
        retailerId: RetailerEnum,
        onSuccess: (isVerified: Boolean) -> Unit,
    ) {
        client.verify(
            retailerId.value,
            { isVerified: Boolean, _: String ->
                onSuccess(isVerified)
            },{ exception ->
                errorHandler(context, exception, call::reject)
                call.reject("Account verification Failed")
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders(
        context: Context,
        call: PluginCall
    ) {
        val req = ReqRetailerAccount(call.data)
        clientVerification(context, call, req.retailerId){
            client.orders(
                req.retailerId.value,
                { _: Int, results: ScanResults?, _: Int, _: String ->
                    if (results != null) {
                        val rsp = RspRetailerOrders(req.retailerId, req.username, results)
                        call.resolve(JSObject.fromJSONObject(rsp.toJson()
                            //.put("isVerified", it)
                            .put("isOrders", true)))
                    }
                },
                { _: Int, exception: AccountLinkingException ->
                    errorHandler(context, exception, call::reject)
                },
            )
        }
    }

    private fun errorHandler(
        context: Context,
        exception: AccountLinkingException,
        reject: (msg: String) -> Unit,
    ){
        when (exception.code){
            INTERNAL_ERROR -> reject("Internal Error")
            INVALID_CREDENTIALS -> reject("Invalid Credentials")
            PARSING_FAILURE -> reject("Parsing Failure")
            USER_INPUT_COMPLETED -> reject("User Input Completed")
            JS_CORE_LOAD_FAILURE -> reject("JS Core Load Failure")
            JS_INVALID_DATA -> reject("JS Invalid Data")
            MISSING_CREDENTIALS -> reject("Missing Credentials")
        }
        if (exception.code == VERIFICATION_NEEDED) {
            if (exception.view != null) {
                val dialog = Dialog(context)
                dialog.setContentView(exception.view!!)
                dialog.show()
            } else {
                reject("Verification Needed")
            }
        }
    }

}
