package com.mytiki.sdk.capture.receipt.capacitor.plugin.req

import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import java.lang.Exception

/**
 * Represents a request to perform a scan operation.
 *
 * @param call The [PluginCall] containing the scan request data.
 */
class ReqScan(call: PluginCall) : Req(call) {
    /**
     * The type of scan to perform, as specified by [ScanTypeEnum].
     */
    val dayCutOff: Int

    init {
        dayCutOff = call.getInt("dayCutOff", 7)!!
    }
}
