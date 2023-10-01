/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.js

import com.getcapacitor.JSObject
import com.microblink.core.Retailer

/**
 * Represents a Retailer for the Receipt Scanning Plugin (RSP).
 *
 * @param retailer The retailer object to be wrapped by JSRetailer.
 */
class JSRetailer(retailer: Retailer) {
    private val id: Int
    private val bannerId: Int

    init {
        id = retailer.id()
        bannerId = retailer.bannerId()
    }

    /**
     * Converts the JSRetailer object to a JSON representation.
     *
     * @return A JSON object representing the JSRetailer.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("id", id)
            .put("bannerId", bannerId)

    companion object {
        /**
         * Create an optional JSRetailer object from a Retailer object.
         *
         * @param retailer The Retailer object to be converted.
         * @return An JSRetailer object if the input Retailer is not null, otherwise null.
         */
        fun opt(retailer: Retailer?): JSRetailer? =
            if (retailer != null) JSRetailer(retailer) else null
    }
}
