/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import com.getcapacitor.JSObject
import com.microblink.core.ScanResults
import com.microblink.linking.*
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
        retailerId: RetailerEnum,
        email: String,
        password: String,
    ): CompletableDeferred<Boolean> {
        val account = Account(
            retailerId.value,
            PasswordCredentials(email, password)
        )
        val isAccountLinked = CompletableDeferred<Boolean>()
        client.link(account)
            .addOnSuccessListener {
                isAccountLinked.complete(it)
            }
            .addOnFailureListener {
                isAccountLinked.completeExceptionally(it)
            }
        return isAccountLinked
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders(
        retailerId: Int,
    ): CompletableDeferred<MutableList<ScanResults>> {
        val orders = CompletableDeferred<MutableList<ScanResults>>()
        val allOrders = mutableListOf<ScanResults>()
        client.orders(
            retailerId,
            success = {retailerId: Int, results: ScanResults?, remaining: Int, uuid: String ->
                if (results != null) {
                    allOrders.add(results)
                }
                if (remaining == 0) {
                    orders.complete(allOrders)
                }
            },
            { retailerId: Int, exception: AccountLinkingException ->
                if (exception.code == VERIFICATION_NEEDED) {
                    orders.completeExceptionally(exception)
                    if (exception.view != null) {
                        binding.webViewContainer.addView(exception.view)
                    }
                }
            }
        )
        return orders
    }

}
