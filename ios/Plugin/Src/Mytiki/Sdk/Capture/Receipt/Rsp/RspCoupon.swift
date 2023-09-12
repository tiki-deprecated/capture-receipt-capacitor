/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

struct RspCoupon : Rsp {
    
    private let type: UInt?
    private let amount: Float?
    private let sku: String?
    private let description: String?
    private let relatedProductIndex: Int
    
    
    init (coupon: BRCoupon){
        type = coupon.couponType.rawValue
        amount = coupon.couponAmount.value
        sku = coupon.couponSku.value
        description = coupon.description
        relatedProductIndex = coupon.relatedProductIndex
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["type"] = type as (any JSValue)?
        ret["amount"] = amount
        ret["sku"] = sku
        ret["description"] = description
        ret["relatedProductIndex"] = relatedProductIndex
        return ret
    }
}
