/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.req

import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall

/**
 * Represents a request to initialize the TIKI SDK for capturing receipts using Capacitor.
 *
 * @property licenseKey The license key required for SDK initialization.
 * @property productKey The product key required for SDK initialization.
 *
 * @constructor Creates an instance of [ReqInitialize] with the provided data.
 *
 * @param call A [PluginCall] containing the initialization data.
 */
class ReqInitialize(call: PluginCall) : Req(call) {
    val productKey: String
    val licenseKey: String

    /**
     * Initializes the [ReqInitialize] object by extracting the licenseKey and productKey
     * from the provided [JSObject] data.
     *
     * @param data A [JSObject] containing the initialization data.
     */
    init {
        productKey = call.getString("productKey")
            ?: throw Error("Provide a Product Intelligence Key for initialization.")
        licenseKey = call.getString("android") ?:
            throw Error("Provide an Android License Key for initialization.")
    }
}
