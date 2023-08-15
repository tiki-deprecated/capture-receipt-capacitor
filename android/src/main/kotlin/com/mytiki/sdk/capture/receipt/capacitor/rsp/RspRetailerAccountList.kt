/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.microblink.digital.PasswordCredentials
import com.microblink.linking.Account
import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum
import org.json.JSONArray
import org.json.JSONObject

class RspRetailerAccountList(results: List<Account>) : Rsp {

    private val accounts: List<RspRetailerAccount>

    init {
        accounts = results.map { account -> RspRetailerAccount(account) }
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("accounts", JSONArray(accounts.map { account -> account.toJson() }))
}
