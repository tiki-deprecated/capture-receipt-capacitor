/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspSurveyAnswer : Rsp {
    private let text: String?
    private let description : String?

    init (surveyAnswer: BRSurveyAnswer) {
        text = surveyAnswer.text
        description = surveyAnswer.description
    }
    
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "text" : text,
            "decription" : description
        ]
    }
}
