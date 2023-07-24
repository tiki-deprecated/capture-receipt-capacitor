/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONObject

class RspLogin(
    private val username: String,
    private val provider: String
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", username)
            .put("provider", provider)
}