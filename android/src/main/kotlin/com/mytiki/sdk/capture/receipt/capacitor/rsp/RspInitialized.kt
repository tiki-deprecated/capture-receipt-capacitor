/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONObject

class RspInitialized(private val isInitialized: Boolean) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("isInitialized", isInitialized)
}