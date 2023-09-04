/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents the response containing a list of [Account] objects.
 *
 * @param accounts The list of [Account] objects.
 * @param error An optional [Exception] object representing an error, if any.
 */
class RspAccountList(
    private val accounts: MutableList<Account>,
    private val error: Exception? = null
) : Rsp {

    /**
     * Converts this response to a JSON object.
     *
     * @return A [JSONObject] representing this response.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("accounts", JSONArray(accounts.map { account -> account.toRsp() }))
            .put("error", error?.message ?: "")
}
