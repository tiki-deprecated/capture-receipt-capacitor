/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspShipment : Rsp {
    private let status: String
    private let products: List<RspProduct>
    
    init(shipment: Shipment){
        status = shipment.status()
        products = shipment.products().map { product -> RspProduct(product) }
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("status", status)
        JSObject.updateValue("products", JSONArray(products.map { prd -> prd.toJson() }))
    }
}
