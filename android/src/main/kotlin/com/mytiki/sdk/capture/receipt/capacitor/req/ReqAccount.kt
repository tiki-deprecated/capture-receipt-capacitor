/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.AccountCommon

class ReqAccount(data: JSObject) {
    val accountType: AccountCommon
    val username: String
    val password: String?
    val isVerified: Boolean?
    init {
        accountType = AccountCommon.fromString(data.getString("source")?: "")
        username = data.getString("username") ?: ""
        password = data.getString("password")
        isVerified = data.getBool("isVerified")
    }
}
