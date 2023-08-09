package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspInitialized
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Retail {
    val link =Link()

    suspend fun initialize(call: PluginCall, context: Context) {
        val req = ReqInitialize(call.data)
        val deferred = MainScope().async {
            link.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            val rsp = RspInitialized(true)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        }
    }
}