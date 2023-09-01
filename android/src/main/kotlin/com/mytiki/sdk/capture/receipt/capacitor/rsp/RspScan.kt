package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONObject

class RspScan(scan: ScanResults?, account: Account? = null, isRunning: Boolean = true) : Rsp {
    private val _account: Account? = account
    private val _scan: RspReceipt? = RspReceipt.opt(scan)
    private val _isRunning: Boolean = isRunning
    override fun toJson(): JSONObject =
        JSONObject()
            .put("account", if(_account != null) RspAccount(_account).toJson() else null )
            .put("receipt", _scan?.toJson())
            .put("isRunning", _isRunning)
}
