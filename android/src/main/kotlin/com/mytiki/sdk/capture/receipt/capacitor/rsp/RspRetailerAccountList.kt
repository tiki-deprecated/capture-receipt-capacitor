/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONArray
import org.json.JSONObject

class RspRetailerAccountList(
    private val accounts: MutableList<RspRetailerAccount>) : Rsp {

    override fun toJson(): JSONObject =
        JSONObject()
            .put("accounts", JSONArray(accounts.map { account -> account.toJson() }))
}
