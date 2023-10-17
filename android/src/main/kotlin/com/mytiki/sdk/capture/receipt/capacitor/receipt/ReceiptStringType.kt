/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.getcapacitor.JSObject
import com.microblink.core.StringType
import org.json.JSONObject

/**
 * Represents a response object for a string type recognized by the TIKI SDK.
 *
 * @param stringType The [StringType] recognized by the TIKI SDK.
 */
class ReceiptStringType(stringType: StringType) {
    /**
     * The confidence score of the recognized string.
     */
    private val confidence: Float

    /**
     * The recognized string value.
     */
    private val value: String?

    init {
        confidence = stringType.confidence()
        value = stringType.value()
    }

    /**
     * Converts the [ReceiptStringType] object to a JSON representation.
     *
     * @return A [JSONObject] containing the confidence and value of the recognized string.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        /**
         * Creates an [ReceiptStringType] object from the provided [StringType].
         *
         * @param stringType The [StringType] to convert.
         * @return An [ReceiptStringType] object representing the provided [StringType], or null if the input is null.
         */
        fun opt(stringType: StringType?): ReceiptStringType? =
            if (stringType != null) ReceiptStringType(stringType) else null
    }
}
