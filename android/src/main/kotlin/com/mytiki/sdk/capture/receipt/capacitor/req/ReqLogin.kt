/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject

class ReqLogin(data: JSObject) {
    val username: String?
    val password: String?
    val provider: String?

    init {
        username = data.getString("username")
        password = data.getString("password")
        provider = data.getString("provider")
    }
}