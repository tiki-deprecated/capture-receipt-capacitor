/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspStringType : Rsp {
    private let confidence: Float
    private let value: String?

    init (stringType: StringType) {
        confidence = stringType.confidence()
        value = stringType.value()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("confidence", confidence)
        JSObject.updateValue("value", value)
    }

}
