/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import com.getcapacitor.JSObject
import com.google.android.gms.tasks.OnSuccessListener
import com.microblink.core.ScanResults
import com.microblink.linking.*
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.internal.toImmutableList

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


    @OptIn(ExperimentalCoroutinesApi::class)
    fun account(
        client: AccountLinkingClient,
        retailerId: Int,
        email: String,
        password: String,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val account = Account(
            retailerId,
            PasswordCredentials(email, password)
        )
        val isAccountLinked = CompletableDeferred<Unit>()
        client.link(account)
            .addOnSuccessListener {
                isAccountLinked.complete(Unit)
            }
            .addOnFailureListener {
                isAccountLinked.completeExceptionally(it)
            }
        return isAccountLinked
    }



}
