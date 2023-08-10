/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspInitialized
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class ReceiptCapture {
    val scan = Scan()
    val email = Email()
    val retailer = Retailer()

    fun initialize(call: PluginCall, context: Context) {
        val req = ReqInitialize(call.data)
        val deferred = MainScope().async {
            scan.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            email.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            retailer.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            val rsp = RspInitialized(true)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        }
    }
}