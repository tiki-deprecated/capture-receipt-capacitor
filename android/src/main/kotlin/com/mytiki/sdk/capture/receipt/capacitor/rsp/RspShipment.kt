/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Shipment
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt Shipment for response purposes.
 *
 * This class encapsulates information about a shipment from a receipt, including its status
 * and the list of products in the shipment.
 *
 * @param shipment The [Shipment] object to be converted to an RspShipment.
 */
class RspShipment(shipment: Shipment) : Rsp {
    private val status: String
    private val products: List<RspProduct>

    /**
     * Initializes an RspShipment instance based on the provided [shipment].
     *
     * @param shipment The [Shipment] object to be converted to an RspShipment.
     */
    init {
        status = shipment.status()
        products = shipment.products().map { product -> RspProduct(product) }
    }

    /**
     * Converts the RspShipment object to a JSON representation.
     *
     * @return A [JSONObject] containing the status and products of the shipment.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("status", status)
            .put("products", JSONArray(products.map { prd -> prd.toJson() }))

    companion object {
        /**
         * Creates an RspShipment object if the provided [shipment] is not null.
         *
         * @param shipment The [Shipment] object to be converted to an RspShipment.
         * @return An [RspShipment] object if [shipment] is not null; otherwise, null.
         */
        fun opt(shipment: Shipment?): RspShipment? =
            if (shipment != null) RspShipment(shipment) else null
    }
}
