/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Retailer
import org.json.JSONObject

/**
 * Represents a Retailer for the Receipt Scanning Plugin (RSP).
 *
 * @param retailer The retailer object to be wrapped by RspRetailer.
 */
class RspRetailer(retailer: Retailer) : Rsp {
    private val id: Int
    private val bannerId: Int

    init {
        id = retailer.id()
        bannerId = retailer.bannerId()
    }

    /**
     * Converts the RspRetailer object to a JSON representation.
     *
     * @return A JSON object representing the RspRetailer.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("bannerId", bannerId)

    companion object {
        /**
         * Create an optional RspRetailer object from a Retailer object.
         *
         * @param retailer The Retailer object to be converted.
         * @return An RspRetailer object if the input Retailer is not null, otherwise null.
         */
        fun opt(retailer: Retailer?): RspRetailer? =
            if (retailer != null) RspRetailer(retailer) else null
    }
}
