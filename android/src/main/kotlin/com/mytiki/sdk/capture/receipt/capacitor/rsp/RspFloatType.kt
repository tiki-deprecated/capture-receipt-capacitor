/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.FloatType
import org.json.JSONObject

class RspFloatType(floatType: FloatType) : Rsp {
    private val confidence: Float
    private val value: Float

    init {
        confidence = floatType.confidence()
        value = floatType.value()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        fun opt(floatType: FloatType?): RspFloatType? =
            if (floatType != null) RspFloatType(floatType) else null
    }
}