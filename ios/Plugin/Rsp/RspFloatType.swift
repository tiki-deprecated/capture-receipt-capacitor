/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspFloatType : Rsp{
    private let confidence: Float
    private let value: Float
    
    
    init(floatType: BRFloatValue) {
        confidence = floatType.confidence
        value = floatType.value
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("confidence", confidence)
        JSObject.updateValue("value", value)
    }
}
