/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspSurveyAnswer : Rsp {
    private let id: Int
    private let text: String?
    private let nextQuestionIndex: Int?

    init (surveyAnswer: SurveyAnswer) {
        id = surveyAnswer.serverId()
        text = surveyAnswer.text()
        nextQuestionIndex = surveyAnswer.nextQuestionIndex()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("id", id)
        JSObject.updateValue("text", text)
        JSObject.updateValue("nextQuestionIndex", nextQuestionIndex)
    }
}
