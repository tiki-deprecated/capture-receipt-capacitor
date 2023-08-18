/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
import Foundation
import Capacitor

struct RspPaymentMethod : Rsp {
    private let paymentMethod: RspStringTyp?
    private let cardType: RspStringType?
    private let cardIssuer: RspStringType?
    private let amount: RspFloatType?
    
    init (paymentMethod: PaymentMethod) {
        self.paymentMethod = RspStringType.opt(paymentMethod.paymentMethod())
        cardType = RspStringType.opt(paymentMethod.cardType())
        cardIssuer = RspStringType.opt(paymentMethod.cardIssuer())
        amount = RspFloatType.opt(paymentMethod.amount())
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("paymentMethod", paymentMethod?.toJson())
        JSObject.updateValue("cardType", cardType?.toJson())
        JSObject.updateValue("cardIssuer", cardIssuer?.toJson())
        JSObject.updateValue("amount", amount?.toJson())
    }
}
