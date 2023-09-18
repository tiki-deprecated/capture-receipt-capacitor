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
class ReqInitialize(data: JSObject) {
    val licenseKey: String
    val productKey: String
    val googleId: String?

    /**
     * Initializes the [ReqInitialize] object by extracting the licenseKey and productKey
     * from the provided [JSObject] data.
     *
     * If the licenseKey or productKey is not present in the data, empty strings are used
     * as defaults.
     *
     * @param data A [JSObject] containing the initialization data.
     */
    init {
        licenseKey = data.getString("licenseKey") ?: ""
        productKey = data.getString("productKey") ?: ""
        googleId = data.getString("googleId")
    }
}
