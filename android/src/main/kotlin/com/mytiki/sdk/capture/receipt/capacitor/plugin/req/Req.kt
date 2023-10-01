package com.mytiki.sdk.capture.receipt.capacitor.plugin.req

import com.getcapacitor.PluginCall
import java.lang.Exception

open class Req(call: PluginCall){
    val requestId: String
    init{
        val reqId = call.getString("requestId")
        if(reqId == null){
            call.reject("Add a requestId in the call.")
            throw(Exception("Add a requestId in the call."))
        }
        requestId = reqId
    }
}