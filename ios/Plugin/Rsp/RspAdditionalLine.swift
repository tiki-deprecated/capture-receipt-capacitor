/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

class RspAdditionalLine : Rsp {
    private let type: BRProductAdditionalLine
    private let text: String
    private let lineNumber: Int
    
    init (additionalLine: BRProductAdditionalLine){
        type = additionalLine.type ?? ""
        text = additionalLine.text ?? ""
        lineNumber = additionalLine.lineNumber
    }
    
    func toJson() -> JSObject {
        JSObject.pupdateValueut("type", type)
        JSObject.updateValue("text", text)
        JSObject.updateValue("lineNumber", lineNumber)
    }
}
