/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject

class ReqRetailerLogin(data: JSObject) {
    val username: String
    val password: String
    val retailer: String

    init {
        username = data.getString("username") ?: ""
        password = data.getString("password") ?: ""
        retailer = data.getString("retailer") ?: ""
    }
}
