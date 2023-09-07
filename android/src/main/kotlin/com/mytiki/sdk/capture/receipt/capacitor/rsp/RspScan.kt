package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONObject

/**
 * Represents a result of the RSP (Receipt Scanning Process) scan operation.
 *
 * @property _account The associated [Account] information if available, or null.
 * @property _scan The scanned receipt information if available, or null.
 * @property _isRunning Indicates whether the scanning process is still running.
 */
class RspScan(
    scan: ScanResults?,
    account: Account? = null,
    isRunning: Boolean = true
) : Rsp {
    private val _account: Account? = account
    private val _scan: RspReceipt? = RspReceipt.opt(scan)
    private val _isRunning: Boolean = isRunning

    /**
     * Converts this [RspScan] object to a JSON representation.
     *
     * @return A [JSONObject] containing the JSON representation of this [RspScan] object.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("account", if (_account != null) RspAccount(_account).toJson() else null)
            .put("receipt", _scan?.toJson())
            .put("isRunning", _isRunning)
}
