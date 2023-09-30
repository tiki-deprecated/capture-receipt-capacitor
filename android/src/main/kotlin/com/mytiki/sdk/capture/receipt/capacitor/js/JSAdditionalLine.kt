/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.js

import com.getcapacitor.JSObject
import com.microblink.core.AdditionalLine

/**
 * Represents a parsed additional line from a receipt. This class is part of the TIKI Inc.
 * SDK for receipt capture.
 *
 * @property type The type of the additional line.
 * @property text The text content of the additional line.
 * @property lineNumber The line number of the additional line within the receipt.
 */
class JSAdditionalLine(additionalLine: AdditionalLine) {
    private val type: JSStringType? = JSStringType.opt(additionalLine.type())
    private val text: JSStringType? = JSStringType.opt(additionalLine.text())
    private val lineNumber: Int = additionalLine.lineNumber()

    /**
     * Converts this `JSAdditionalLine` object to a JSON representation.
     *
     * @return A `JSONObject` representing the additional line.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("type", type?.toJS())
            .put("text", text?.toJS())
            .put("lineNumber", lineNumber)

    companion object {
        /**
         * Creates an `JSAdditionalLine` object from an `AdditionalLine` if the input is not null.
         *
         * @param additionalLine The `AdditionalLine` to convert.
         * @return An `JSAdditionalLine` object, or null if the input is null.
         */
        fun opt(additionalLine: AdditionalLine?): JSAdditionalLine? =
            if (additionalLine != null) JSAdditionalLine(additionalLine) else null
    }
}
