/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.plugin.PluginEvent
import org.json.JSONObject

/**
 * Represents a Receipt Service Provider (RSP) account.
 *
 * @property account The [Account] associated with this RSP account.
 */
class RspAccount(
    requestId: String,
    private val account: Account
) : Rsp(requestId, PluginEvent.onAccount) {

    /**
     * Converts the RSP account data to a JSON object.
     *
     * @return A [JSONObject] representing the RSP account.
     */
    override fun toJS(): JSObject =
        super.toJS()
            .put("payload", JSObject()
                .put("username", account.username)
                .put("id", account.accountCommon.id)
                .put("type", account.accountCommon.type.name)
                .put("isVerified", account.isVerified)
            )
}
