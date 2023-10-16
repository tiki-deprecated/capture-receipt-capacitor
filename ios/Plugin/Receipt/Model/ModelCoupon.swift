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
struct ModelCoupon {
    /// The type of the coupon.
    private let type: String?
    
    /// The coupon amount, if available.
    private let amount: ModelFloatType?
    
    /// The SKU (Stock Keeping Unit) associated with the coupon, if available.
    private let sku: ModelStringType?
    
    /// The description or text associated with the coupon, if available.
    private let description: ModelStringType?
    
    /// The index of the related product associated with this coupon.
    private let relatedProductIndex: Int

    /**
     Initializes an `RspCoupon` struct based on a `BRCoupon` object.

     - Parameter coupon: A `BRCoupon` object containing coupon information.
     */
    init(coupon: BRCoupon) {
        type = String(describing: coupon.couponType)
        amount = ModelFloatType(floatType: coupon.couponAmount)
        sku = ModelStringType(stringType: coupon.couponSku)
        description = ModelStringType(string: coupon.description)
        relatedProductIndex = coupon.relatedProductIndex
    }
    
    static func opt(coupon: BRCoupon?) -> JSCoupon? {
        return coupon != nil ? JSCoupon(coupon: coupon!) : nil
    }

    /**
     Converts the `RspCoupon` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary representing coupon details, including its type, amount, SKU, description, and related product index, in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
        var result = JSObject()
        result["type"] = type
        result["amount"] = amount?.toJSObject()
        result["sku"] = sku?.toJSObject()
        result["description"] = description?.toJSObject()
        result["relatedProductIndex"] = relatedProductIndex
        return result
    }
}
