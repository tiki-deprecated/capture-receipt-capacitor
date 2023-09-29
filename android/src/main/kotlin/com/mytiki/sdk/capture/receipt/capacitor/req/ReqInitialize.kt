/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject

/**
 * Represents a request to initialize the TIKI SDK for capturing receipts using Capacitor.
 *
 * @property licenseKey The license key required for SDK initialization.
 * @property productKey The product key required for SDK initialization.
 *
 * @constructor Creates an instance of [ReqInitialize] with the provided data.
 *
 * @param data A [JSObject] containing the initialization data.
 */
class ReqInitialize(data: JSObject) : Req(data.getString("requestId") ?: "") {
    val licenseKey: String
    val productKey: String

    /**
     * Initializes the [ReqInitialize] object by extracting the licenseKey and productKey
     * from the provided [JSObject] data.
     *
     * @param data A [JSObject] containing the initialization data.
     */
    init {
        val licenseKey = data.getString("licenseKey")
        val productKey = data.getString("productKey")
        if (licenseKey == null) {
            throw Error("Provide a License Key for initialization.")
        }
        if (productKey == null) {
            throw Error("Provide a Product Intelligence Key for initialization.")
        }
        this.productKey = productKey
        this.licenseKey = licenseKey
    }
}
