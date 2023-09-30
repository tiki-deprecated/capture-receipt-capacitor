/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.PluginEvent
import org.json.JSONObject
open class Rsp(val requestId: String) {
    open fun toJS(): JSObject {
        return JSObject()
            .put("requestId", this.requestId)
    }
}
