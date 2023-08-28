/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.AccountCommon

class ReqAccount(data: JSObject) {
    val accountCommon: AccountCommon
    val username: String
    val password: String?
    val isVerified: Boolean?
    init {
        accountCommon = AccountCommon.fromString(data.getString("source")?: "")
        username = data.getString("username") ?: ""
        password = data.getString("password")
        isVerified = data.getBool("isVerified")
    }
}
