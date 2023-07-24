/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.fixtures

import com.getcapacitor.JSObject
import com.getcapacitor.MessageHandler
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import org.json.JSONObject
import java.util.UUID


class PluginCallBuilder(val req: JSONObject? = null) {
    val complete: CompletableDeferred<JSONObject> = CompletableDeferred()

    fun build(): PluginCall {
        val handler = mockk<MessageHandler>(relaxed = true)
        every { handler.sendResponseMessage(any(), any(), any()) }
            .answers { call ->
                val res: PluginResult? = call.invocation.args[1] as PluginResult?
                val err: PluginResult? = call.invocation.args[2] as PluginResult?
                if (res != null) complete.complete(JSONObject(res.toString()))
                else if (err != null) complete.complete(JSONObject(err.toString()))
                else complete.complete(JSONObject())
            }
        return PluginCall(
            handler,
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            if (req != null) JSObject.fromJSONObject(req) else JSObject()
        );
    }
}