/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.req

import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon

/**
 * Represents a request object for account-related operations in the TIKI SDK.
 *
 * @param data A [JSObject] containing the data for the account request.
 */
class ReqAccount(call: PluginCall) : Req(call) {
    /**
     * The [AccountCommon] object associated with this account request.
     */
    val accountCommon: AccountCommon

    /**
     * The username for the account request.
     */
    val username: String

    /**
     * The password for the account request (nullable).
     */
    val password: String?

    /**
     * Indicates whether the account is verified (nullable).
     */
    val isVerified: Boolean?

    init {
        accountCommon = AccountCommon.fromSource(call.getString("id") ?: "")
        username = call.getString("username") ?: ""
        password = call.getString("password")
        isVerified = call.getBoolean("isVerified")
    }
}
