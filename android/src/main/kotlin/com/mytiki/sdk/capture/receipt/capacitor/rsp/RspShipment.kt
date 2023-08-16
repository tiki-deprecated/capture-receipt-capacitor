/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSArray
import com.microblink.core.Shipment
import org.json.JSONObject

class RspShipment(shipment: Shipment) : Rsp {
    private val status: String
    private val products: List<RspProduct>

    init {
        status = shipment.status()
        products = shipment.products().map { product -> RspProduct(product) }
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("status", status)
            .put("products", JSArray.from(products.map { prd -> prd.toJson() }))

    companion object {
        fun opt(shipment: Shipment?): RspShipment? =
            if (shipment != null) RspShipment(shipment) else null
    }
}