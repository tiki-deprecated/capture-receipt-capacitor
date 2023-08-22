/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.AccountTypeEnum

class ReqAccount(data: JSObject) {
    val accountType: AccountTypeEnum
    val username: String
    val password: String?
    val isVerified: Boolean?
    init {
        accountType = AccountTypeEnum.fromString(data.getString("source")?: "")
        username = data.getString("username") ?: ""
        password = data.getString("password")
        isVerified = data.getBool("isVerified")
    }
}
