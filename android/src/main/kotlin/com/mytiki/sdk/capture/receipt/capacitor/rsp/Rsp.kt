/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.PluginEvent
import org.json.JSONObject

/**
 * This interface represents the Receipt Capture Service Provider (RSP) for the TIKI SDK.
 * RSP providers must implement this interface and provide an implementation for the [toJson] function.
 */
interface Rsp {

    /**
     * Converts the data of this RSP implementation into a JSON object.
     *
     * @return A [JSONObject] containing the serialized data of this RSP.
     */
    fun toJS(): JSObject
}
