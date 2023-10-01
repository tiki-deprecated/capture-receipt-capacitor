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

    /**
     * The optional [Account] associated with the scan request, or `null` if not provided.
     */
    val account: Account?

    init {
        dayCutOff = call.getInt("dayCutOff", 7)!!
        account =
            if (call.getString("username").isNullOrBlank() ){
               call.reject("Provide a username in the plugin call.")
               throw(Exception("Provide a username in the plugin call."))
            } else if (call.getString("id").isNullOrBlank()) {
                call.reject("Provide an id in the plugin call to identify the Account Type.")
                throw(Exception("Provide an id in the plugin call to identify the Account Type."))
            } else {
                Account(
                    AccountCommon.fromSource(call.getString("id")!!),
                    call.getString("username")!!,
                    call.getString("password"),
                    call.getBoolean("isVerified")
                )
            }
    }
}
