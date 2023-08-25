/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONObject

class RspAccount(
    private val account: Account
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", account.username)
            .put("source", account.accountCommon.source)
            .put("type", account.accountCommon.type.name)
            .put("isVerified", account.isVerified)
}