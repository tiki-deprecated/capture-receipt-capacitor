/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.microblink.core.InitializeCallback
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspInitialized
import kotlinx.coroutines.CompletableDeferred

class OnInitialize(
    private val completable: CompletableDeferred<Unit>,
    private val onError: (msg: String?, data: JSObject) -> Unit,
) : InitializeCallback {

    override fun onComplete() {
        completable.complete(Unit)
    }

    override fun onException(throwable: Throwable) {
        val rsp = RspInitialized(false)
        onError(throwable.message, JSObject.fromJSONObject(rsp.toJson()))
        completable.complete(Unit)
    }
}