/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import com.getcapacitor.JSObject
import com.microblink.linking.AccountLinkingClient
import com.microblink.linking.BlinkReceiptLinkingSdk
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

class Retailer {

    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isLinkInitialized = CompletableDeferred<Unit>()
        BlinkReceiptLinkingSdk.licenseKey = req.licenseKey!!
        BlinkReceiptLinkingSdk.productIntelligenceKey = req.productKey!!
        BlinkReceiptLinkingSdk.initialize(context, OnInitialize(isLinkInitialized, onError))
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

}
