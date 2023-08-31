/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspPaymentMethod : Rsp {
    
    private let paymentMethod: String?
    private let cardType: String?
    private let cardIssuer: String?
    private let amount: Float?
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["paymentMethod"] = paymentMethod
        ret["cardType"] = cardType
        ret["cardIssuer"] = cardIssuer
        ret["amount"] = amount
        return ret
    }
}
