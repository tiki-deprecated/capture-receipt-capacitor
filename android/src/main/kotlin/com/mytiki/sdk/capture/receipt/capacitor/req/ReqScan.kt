package com.mytiki.sdk.capture.receipt.capacitor.req

import com.mytiki.sdk.capture.receipt.capacitor.ScanTypeEnum
import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.Account
import com.mytiki.sdk.capture.receipt.capacitor.AccountCommon

/**
 * Represents a request to perform a scan operation.
 *
 * @param data The [JSObject] containing the scan request data.
 */
class ReqScan(data: JSObject) {
    /**
     * The type of scan to perform, as specified by [ScanTypeEnum].
     */
    val scanType: ScanTypeEnum

    /**
     * The optional [Account] associated with the scan request, or `null` if not provided.
     */
    val account: Account?

    init {
        // Initialize the scanType property by parsing the "scanType" field from the JSObject.
        scanType = ScanTypeEnum.fromString(data.getString("scanType") ?: "")

        // Initialize the account property based on the provided data.
        account = if (data.getString("username").isNullOrEmpty() == false && data.getString("source") != "") {
            Account(
                AccountCommon.fromSource(data.getString("source") ?: ""),
                data.getString("username") ?: "",
                data.getString("password"),
                data.getBool("isVerified")
            )
        } else {
            null
        }
    }
}
