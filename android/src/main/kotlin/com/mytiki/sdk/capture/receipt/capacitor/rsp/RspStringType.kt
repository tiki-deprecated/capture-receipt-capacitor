/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.StringType
import org.json.JSONObject

class RspStringType(stringType: StringType) : Rsp {
    private val confidence: Float
    private val value: String?

    init {
        confidence = stringType.confidence()
        value = stringType.value()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        fun opt(stringType: StringType?): RspStringType? =
            if (stringType != null) RspStringType(stringType) else null
    }
}