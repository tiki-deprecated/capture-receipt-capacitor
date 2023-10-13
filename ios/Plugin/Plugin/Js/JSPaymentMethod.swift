/*
 * RspPaymentMethod Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

/**
 Represents a response containing payment method information.

 This struct is used to convey payment method details, such as payment method type, card type, card issuer, and amount.
 */
public struct JSPaymentMethod {
    /// The payment method, if available.
    private let paymentMethod: JSStringType?
    /// The card type, if available.
    private let cardType: JSStringType?
    /// The card issuer, if available.
    private let cardIssuer: JSStringType?
    /// The payment amount, if available.
    private let amount: JSFloatType?
    
    /**
     Initializes an `RspPaymentMethod` struct.

     - Parameters:
        - paymentMethod: The payment method, if available.
        - cardType: The card type, if available.
        - cardIssuer: The card issuer, if available.
        - amount: The payment amount, if available.
     */
    init(method: BRPaymentMethod) {
        self.paymentMethod = JSStringType(stringType: method.method)
        self.cardType = JSStringType(stringType: method.cardType)
        self.cardIssuer = JSStringType(stringType: method.cardIssuer)
        self.amount = JSFloatType(floatType: method.amount)
    }
    
    /**
     Converts the `RspPaymentMethod` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing payment method information in a format suitable for a Capacitor plugin call result.
     */
    public func toJSObject() -> JSObject {
        var ret = JSObject()
        ret["paymentMethod"] = paymentMethod?.toJSObject()
        ret["cardType"] = cardType?.toJSObject()
        ret["cardIssuer"] = cardIssuer?.toJSObject()
        ret["amount"] = amount?.toJSObject()
        return ret
    }
}
