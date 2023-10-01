package com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.plugin.PluginEvent
import org.json.JSONObject

class RspError(
    requestId: String,
    val message: String,
    val code: RspErrorEnum? = RspErrorEnum.ERROR
): Rsp(requestId, PluginEvent.onError) {

    /**
     * Converts the RSP account data to a JSON object.
     *
     * @return A [JSONObject] representing the RSP account.
     */
    override fun toJS(): JSObject = super.toJS()
        .put("payload", JSObject()
            .put("message", this.message)
            .put("code", this.code)
        )
}