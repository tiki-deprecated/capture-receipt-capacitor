/*
 * RspCoupon Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

/**
 Represents a response containing coupon information for the ReceiptCapture plugin.

 This struct is used to encapsulate coupon details, including its type, amount, SKU, description, and related product index, in response to plugin calls.
 */
struct RspCoupon: Rsp {
    /// The type of the coupon.
    private let type: UInt?
    
    /// The coupon amount, if available.
    private let amount: Float?
    
    /// The SKU (Stock Keeping Unit) associated with the coupon, if available.
    private let sku: String?
    
    /// The description or text associated with the coupon, if available.
    private let description: String?
    
    /// The index of the related product associated with this coupon.
    private let relatedProductIndex: Int

    /**
     Initializes an `RspCoupon` struct based on a `BRCoupon` object.

     - Parameter coupon: A `BRCoupon` object containing coupon information.
     */
    init(coupon: BRCoupon) {
        type = coupon.couponType.rawValue
        amount = coupon.couponAmount.value
        sku = coupon.couponSku.value
        description = coupon.description
        relatedProductIndex = coupon.relatedProductIndex
    }

    /**
     Converts the `RspCoupon` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary representing coupon details, including its type, amount, SKU, description, and related product index, in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var result = JSObject()
        result["type"] = type as JSValue?
        result["amount"] = amount
        result["sku"] = sku
        result["description"] = description
        result["relatedProductIndex"] = relatedProductIndex
        return result
    }
}
