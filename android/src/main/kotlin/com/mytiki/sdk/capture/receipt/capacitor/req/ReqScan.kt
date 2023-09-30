package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.Account
import com.mytiki.sdk.capture.receipt.capacitor.AccountCommon

/**
 * Represents a request to perform a scan operation.
 *
 * @param data The [JSObject] containing the scan request data.
 */
class ReqScan(data: JSObject) : Req(data.getString("requestId") ?: "") {
    /**
     * The type of scan to perform, as specified by [ScanTypeEnum].
     */
    val dayCutOff: Int

    /**
     * The optional [Account] associated with the scan request, or `null` if not provided.
     */
    val account: Account?

    init {
        dayCutOff = data.getInteger("dayCutOff", 7)!!
        account =
            if (!data.getString("username").isNullOrEmpty() && data.getString("id") != "") {
                Account(
                    AccountCommon.fromSource(data.getString("id") ?: ""),
                    data.getString("username") ?: "",
                    data.getString("password"),
                    data.getBool("isVerified")
                )
            } else {
                null
            }
    }
}
