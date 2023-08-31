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
    private let products: [RspProduct]
    
    init(shipment: BRShipment){
        status = shipment.status
        products = shipment.products.map { product in RspProduct(product: product) }
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["status"] = status
        ret["products"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() })
    }
}
