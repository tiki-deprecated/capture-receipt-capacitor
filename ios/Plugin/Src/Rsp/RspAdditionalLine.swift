/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

class RspAdditionalLine : Rsp {
    
    private let type: BRStringValue
    private let text: BRStringValue
    private let lineNumber: Int
    
    init (additionalLine: BRProductAdditionalLine){
        type = additionalLine.type
        text = additionalLine.text
        lineNumber = additionalLine.lineNumber
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["type"] = type.value
        ret["text"] = text.value
        ret["lineNumber"] = lineNumber
        return ret
    }

}
