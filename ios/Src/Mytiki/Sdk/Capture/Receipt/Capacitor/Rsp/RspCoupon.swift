//
//  RspCoupon.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

struct RspCoupon : Rsp {
    private let type: String?
    private let amount: BRFloatValue?
    private let sku: BRStringValue?
    private let description: BRStringValue?
    private let relatedProductIndex: Int
    
    
    init (coupon: BRCoupon){
        type = coupon.couponType
        amount = coupon.couponAmount
        sku = coupon.couponSku
        description = coupon.description
        relatedProductIndex = coupon.relatedProductIndex
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("type", type)
        JSObject.updateValue("amount", amount)
        JSObject.updateValue("sku", sku)
        JSObject.updateValue("description", description)
        JSObject.updateValue("relatedProductIndex", relatedProductIndex)
    }
}
