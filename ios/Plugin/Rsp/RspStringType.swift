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

    init (stringType: BRStringValue) {
        confidence = stringType.confidence
        value = stringType.value
    }
    
    public func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "confidence" : confidence,
            "value" : value ?? ""
        ]
    }

}
