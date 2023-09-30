/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.js

import com.getcapacitor.JSObject
import com.microblink.core.Shipment
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt Shipment for response purposes.
 *
 * This class encapsulates information about a shipment from a receipt, including its status
 * and the list of products in the shipment.
 *
 * @param shipment The [Shipment] object to be converted to an JSShipment.
 */
class JSShipment(shipment: Shipment)  {
    private val status: String
    private val products: List<JSProduct>

    /**
     * Initializes an JSShipment instance based on the provided [shipment].
     *
     * @param shipment The [Shipment] object to be converted to an JSShipment.
     */
    init {
        status = shipment.status()
        products = shipment.products().map { product -> JSProduct(product) }
    }

    /**
     * Converts the JSShipment object to a JSON representation.
     *
     * @return A [JSONObject] containing the status and products of the shipment.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("status", status)
            .put("products", JSONArray(products.map { prd -> prd.toJS() }))

    companion object {
        /**
         * Creates an JSShipment object if the provided [shipment] is not null.
         *
         * @param shipment The [Shipment] object to be converted to an JSShipment.
         * @return An [JSShipment] object if [shipment] is not null; otherwise, null.
         */
        fun opt(shipment: Shipment?): JSShipment? =
            if (shipment != null) JSShipment(shipment) else null
    }
}
