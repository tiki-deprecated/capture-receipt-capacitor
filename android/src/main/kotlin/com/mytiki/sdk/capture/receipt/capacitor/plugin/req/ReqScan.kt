package com.mytiki.sdk.capture.receipt.capacitor.plugin.req

import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import java.lang.Exception

/**
 * Represents a request to perform a scan operation.
 *
 * @property dayCutOff The day cutoff for scanning receipts (default is 7 days).
 *
 * @constructor Creates an instance of [ReqScan] with the provided data.
 *
 * @param call A [PluginCall] containing the scan request data.
 */
class ReqScan(call: PluginCall) : Req(call) {
    val dayCutOff: Int

    /**
     * Initializes the [ReqScan] object by extracting the dayCutOff parameter from the provided [PluginCall].
     * If the parameter is not provided, the default value is set to 7.
     *
     * @param call A [PluginCall] containing the scan request data.
     */
    init {
        dayCutOff = call.getInt("dayCutOff", 7) ?: 7
    }
}
