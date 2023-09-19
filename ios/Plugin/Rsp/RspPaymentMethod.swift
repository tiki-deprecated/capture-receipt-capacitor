/*
 * RspPaymentMethod Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

/**
 Represents a response containing payment method information.

 This struct is used to convey payment method details, such as payment method type, card type, card issuer, and amount.
 */
public struct RspPaymentMethod: Rsp {
    /// The payment method, if available.
    private let paymentMethod: String?
    /// The card type, if available.
    private let cardType: String?
    /// The card issuer, if available.
    private let cardIssuer: String?
    /// The payment amount, if available.
    private let amount: Float?
    
    /**
     Initializes an `RspPaymentMethod` struct.

     - Parameters:
        - paymentMethod: The payment method, if available.
        - cardType: The card type, if available.
        - cardIssuer: The card issuer, if available.
        - amount: The payment amount, if available.
     */
    init(paymentMethod: String?, cardType: String?, cardIssuer: String?, amount: Float?) {
        self.paymentMethod = paymentMethod
        self.cardType = cardType
        self.cardIssuer = cardIssuer
        self.amount = amount
    }
    
    /**
     Converts the `RspPaymentMethod` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing payment method information in a format suitable for a Capacitor plugin call result.
     */
    public func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["paymentMethod"] = paymentMethod
        ret["cardType"] = cardType
        ret["cardIssuer"] = cardIssuer
        ret["amount"] = amount
        return ret
    }
}
