/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.AdditionalLine
import org.json.JSONObject

class RspAdditionalLine(additionalLine: AdditionalLine) : Rsp {
    private val type: RspStringType?
    private val text: RspStringType?
    private val lineNumber: Int

    init {
        type = RspStringType.opt(additionalLine.type())
        text = RspStringType.opt(additionalLine.text())
        lineNumber = additionalLine.lineNumber()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("type", type?.toJson())
            .put("text", text?.toJson())
            .put("lineNumber", lineNumber)

    companion object {
        fun opt(additionalLine: AdditionalLine?): RspAdditionalLine? =
            if (additionalLine != null) RspAdditionalLine(additionalLine) else null
    }
}