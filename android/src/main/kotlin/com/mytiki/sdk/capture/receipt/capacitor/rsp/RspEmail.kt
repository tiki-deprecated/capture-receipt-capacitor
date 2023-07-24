/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.microblink.digital.PasswordCredentials
import org.json.JSONObject

class RspEmail(credential: PasswordCredentials, results: List<ScanResults>) : Rsp {
    private val login: RspLogin
    private val scans: List<RspScan>

    init {
        login = RspLogin(credential.username(), credential.provider().type())
        scans = results.map { scan -> RspScan(scan) }
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("login", login.toJson())
            .put("scans", scans.map { scan -> scan.toJson() })
}