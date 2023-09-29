/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

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
class RspAdditionalLine(additionalLine: AdditionalLine) {
    private val type: RspStringType? = RspStringType.opt(additionalLine.type())
    private val text: RspStringType? = RspStringType.opt(additionalLine.text())
    private val lineNumber: Int = additionalLine.lineNumber()

    /**
     * Converts this `RspAdditionalLine` object to a JSON representation.
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
         * Creates an `RspAdditionalLine` object from an `AdditionalLine` if the input is not null.
         *
         * @param additionalLine The `AdditionalLine` to convert.
         * @return An `RspAdditionalLine` object, or null if the input is null.
         */
        fun opt(additionalLine: AdditionalLine?): RspAdditionalLine? =
            if (additionalLine != null) RspAdditionalLine(additionalLine) else null
    }
}
