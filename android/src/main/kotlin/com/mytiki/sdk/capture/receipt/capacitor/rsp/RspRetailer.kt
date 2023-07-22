/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Retailer
import org.json.JSONObject

class RspRetailer(retailer: Retailer) : Rsp {
    private val id: Int
    private val bannerId: Int

    init {
        id = retailer.id()
        bannerId = retailer.bannerId()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("bannerId", bannerId)

    companion object {
        fun opt(retailer: Retailer?): RspRetailer? =
            if (retailer != null) RspRetailer(retailer) else null
    }
}