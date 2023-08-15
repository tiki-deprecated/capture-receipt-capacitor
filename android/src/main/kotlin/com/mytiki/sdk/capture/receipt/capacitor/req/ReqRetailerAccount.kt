/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum

class ReqRetailerAccount(data: JSObject) {
    val username: String
    val password: String
    val retailerId: RetailerEnum

    init {
        username = data.getString("username").toString()
        password = data.getString("password").toString()
        retailerId = RetailerEnum.fromInt(data.getInt("retailerId"))
    }
}
