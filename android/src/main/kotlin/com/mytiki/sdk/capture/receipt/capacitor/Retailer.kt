/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.R
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
    fun account(call: PluginCall, onFinish: () -> Unit = {}){
        val req = ReqRetailerAccount(call.data)
        val account = Account(
            req.retailerId.value,
            PasswordCredentials(req.username!!, req.password!!)
        )
        client.link(account)
            .addOnSuccessListener {
                clientVerification(
                    req.retailerId,
                    {
                        val rsp = RspRetailerAccount(account)
                        call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                        onFinish()
                    },{
                        call.reject("Verification Failed")
                        onFinish()
                    }
                )
            }
            .addOnFailureListener {
                call.reject(it.message)
                onFinish()
            }
        client.link(account).addOnSuccessListener {
            clientVerification(
                req.retailerId,
                {
                    val rsp = RspRetailerAccount(account)
                    call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                },{
                    call.reject("Verification Failed")
                }
            )
        }
        .addOnFailureListener {
            call.reject(it.message)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun accountList(call: PluginCall){
        val allAccounts = client.accounts().result
        if (allAccounts != null){
            val rsp = RspRetailerAccountList(allAccounts)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        } else{
            call.reject("Failed to retrieve account list")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun accountRemove(call: PluginCall){
        val req = ReqRetailerAccount(call.data)
        val account = Account(
            req.retailerId.value,
            PasswordCredentials(req.username!!, req.password!!)
        )
        client.unlink(account).addOnSuccessListener {
            val rsp = RspRetailerAccount(account)
            call.resolve(JSObject.fromJSONObject(rsp.toJson().put("isAccountRemoved", it)))
        }.addOnFailureListener {
            call.reject(it.message)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clientVerification(
        retailerId: RetailerEnum,
        onSuccess: () -> Unit,
        onError: (exception: AccountLinkingException) -> Unit
    ) {
        client.verify(
            retailerId.value,
            { _: Boolean, _: String ->
                onSuccess()
            },{ exception ->
                onError(exception)
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders(
        context: Context,
        call: PluginCall
    ): CompletableDeferred<Unit> {
        val req = ReqRetailerAccount(call.data)
        val account = Account(
            req.retailerId.value,
            PasswordCredentials(req.username!!, req.password!!)
        )
        val orders = CompletableDeferred<Unit>()
        clientVerification(
            req.retailerId,
            {
                client.orders(
                    req.retailerId.value,
                    { _: Int, results: ScanResults?, _: Int, _: String ->
                        if (results != null) {
                            val rsp = RspRetailerOrders(account, results)
                            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                            orders.complete(Unit)
                        }
                    },
                    { _: Int, exception: AccountLinkingException ->

                        errorHandler(context, exception, call::reject)
                    },
                )
            },{ exception: AccountLinkingException ->
                errorHandler(context, exception, call::reject)
                call.reject("Verification Failed")
                orders.completeExceptionally(exception)
            }
        )
        return orders
    }

    private fun errorHandler(
        context: Context,
        exception: AccountLinkingException,
        reject: (msg: String) -> Unit,
    ){
        if (exception.code == VERIFICATION_NEEDED) {
            if (exception.view != null) {
                val dialog = Dialog(context)
                dialog.setContentView(exception.view!!)
                dialog.show()
            } else {
                reject("Verification Needed")
            }
        }
        when (exception.code){
            INTERNAL_ERROR -> reject("Internal Error")
            INVALID_CREDENTIALS -> reject("Invalid Credentials")
            PARSING_FAILURE -> reject("Parsing Failure")
            USER_INPUT_COMPLETED -> reject("User Input Completed")
            JS_CORE_LOAD_FAILURE -> reject("JS Core Load Failure")
            JS_INVALID_DATA -> reject("JS Invalid Data")
            MISSING_CREDENTIALS -> reject("Missing Credentials")
            else -> reject("Unknown Error")
        }
    }

}
