/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONObject

/**
 * Represents a Receipt Service Provider (RSP) account.
 *
 * @property account The [Account] associated with this RSP account.
 */
class RspAccount(
    private val account: Account
) : Rsp {

    /**
     * Converts the RSP account data to a JSON object.
     *
     * @return A [JSONObject] representing the RSP account.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", account.username)
            .put("source", account.accountCommon.source)
            .put("type", account.accountCommon.type.name)
            .put("isVerified", account.isVerified)
}
