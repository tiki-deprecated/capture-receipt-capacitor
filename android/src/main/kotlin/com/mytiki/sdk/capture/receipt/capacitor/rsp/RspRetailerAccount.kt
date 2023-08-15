/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.linking.Account
import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum
import org.json.JSONObject

class RspRetailerAccount(
    private val account: Account,
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", account.credentials.username())
            .put("retailerId", account.retailerId)

}
