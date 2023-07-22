/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject

class ReqInitialize(data: JSObject) {
    val licenseKey: String?

    init {
        licenseKey = data.getString("licenseKey")
    }
}