/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.plugin.PluginEvent

open class Rsp(val requestId: String, val event: PluginEvent) {
    open fun toJS(): JSObject = JSObject()
            .put("requestId", this.requestId)
            .put("event", this.event)
}
