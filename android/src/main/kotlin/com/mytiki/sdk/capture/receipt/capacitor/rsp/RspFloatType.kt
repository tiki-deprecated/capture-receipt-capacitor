/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.microblink.core.FloatType

/**
 * Represents a response (Rsp) for a floating-point value extracted from a receipt.
 *
 * @property confidence The confidence level of the extracted floating-point value.
 * @property value The extracted floating-point value.
 */
class RspFloatType(private val floatType: FloatType) : Rsp {
    private val confidence: Float = floatType.confidence()
    private val value: Float = floatType.value()

    /**
     * Converts the RspFloatType object to a JSON representation.
     *
     * @return A JSONObject containing the confidence and value of the floating-point type.
     */
    override fun toJS(): JSObject =
        JSObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        /**
         * Creates an RspFloatType instance from a FloatType object, if it is not null.
         *
         * @param floatType The FloatType object to convert to RspFloatType.
         * @return An RspFloatType instance or null if the input floatType is null.
         */
        fun opt(floatType: FloatType?): RspFloatType? =
            if (floatType != null) RspFloatType(floatType) else null
    }
}
