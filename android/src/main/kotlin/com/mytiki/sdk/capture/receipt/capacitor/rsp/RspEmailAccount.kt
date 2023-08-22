/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONObject

class RspEmailAccount(
    private val username: String,
    private val provider: String,
    private val verified: Boolean
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", username)
            .put("provider", provider)
            .put("verified", verified)
}