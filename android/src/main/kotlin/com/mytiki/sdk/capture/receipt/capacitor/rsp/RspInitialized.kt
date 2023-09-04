/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONObject

/**
 * Represents the response data for the initialization of a specific component.
 *
 * @property isInitialized Indicates whether the component is initialized.
 */
class RspInitialized(private val isInitialized: Boolean) : Rsp {

    /**
     * Converts the response data to a JSON object.
     *
     * @return A JSON object representing the response data.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("isInitialized", isInitialized)
}
