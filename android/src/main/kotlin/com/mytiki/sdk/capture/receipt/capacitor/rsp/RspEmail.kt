/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.microblink.digital.PasswordCredentials
import org.json.JSONArray
import org.json.JSONObject

class RspEmail(credential: PasswordCredentials, results: List<ScanResults>) : Rsp {
    private val login: RspLogin
    private val scans: List<RspReceipt>

    init {
        login = RspLogin(credential.username(), credential.provider().name)
        scans = results.map { scan -> RspReceipt(scan) }
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("login", login.toJson())
            .put("scans", JSONArray(scans.map { scan -> scan.toJson() }))
}