package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONArray
import org.json.JSONObject

class RspOnlineScan(account: Account, scan: ScanResults, isRunning: Boolean = true) : Rsp {
    private val _account: Account = account
    private val _scan: RspScan = RspScan(scan)
    private val _isRunning: Boolean = isRunning
    override fun toJson(): JSONObject =
        JSONObject()
            .put("account", RspAccount(_account).toJson())
            .put("scan", _scan.toJson())
            .put("isRunning", _isRunning)

}
