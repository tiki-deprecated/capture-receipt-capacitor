/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum
import org.json.JSONObject

class RspRetailerAccount(
    private val username: String,
    private val retailerId: RetailerEnum
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", username)
            .put("retailerId", retailerId.value)

}
