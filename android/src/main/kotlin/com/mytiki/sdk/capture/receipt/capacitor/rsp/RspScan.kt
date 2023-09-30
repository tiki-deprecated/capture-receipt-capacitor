package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
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
    requestId: String,
    scan: ScanResults?,
    account: Account? = null,
    isRunning: Boolean = true
) : Rsp(requestId) {
    private val _account: Account? = account
    private val _scan: RspReceipt? = RspReceipt.opt(requestId, scan)
    private val _isRunning: Boolean = isRunning

    /**
     * Converts this [RspScan] object to a JSON representation.
     *
     * @return A [JSONObject] containing the JSON representation of this [RspScan] object.
     */
    override fun toJS(): JSObject =
        JSObject()
            .put("account", if (_account != null) RspAccount(this.requestId, _account).toJS() else null)
            .put("receipt", _scan?.toJS())
            .put("isRunning", _isRunning)
}
