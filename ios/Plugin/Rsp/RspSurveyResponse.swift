/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt


struct RspSurveyResponse : Rsp {
    let answersSelected : [Int]
    let freeText : String
    
    init(answersSelected: [Int], freeText: String) {
        self.answersSelected = answersSelected
        self.freeText = freeText
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["answersSelected"] = JSArray(answersSelected.map { answerSelected in answersSelected })
        ret["freeText"] = freeText
        return ret
    }
    
    
}
