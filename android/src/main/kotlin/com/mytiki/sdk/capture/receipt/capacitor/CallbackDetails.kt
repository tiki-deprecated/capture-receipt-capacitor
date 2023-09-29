package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.rsp.Rsp

class CallbackDetails(
    /**
     * A unique identifier of the {@link Req} to which this callback should listen.
     */
    val requestId: String,
    /**
     * The {@link PluginEvent} that triggered the callback.
     */
    val event: PluginEvent,

    /**
     * The payload returned. Should be undefined on plugin requests.
     */
    val payload: JSObject
){
    fun toJS(): JSObject {
        return JSObject()
            .put("requestId", requestId)
            .put("event", event.name)
            .put("payload", payload)
    }
}