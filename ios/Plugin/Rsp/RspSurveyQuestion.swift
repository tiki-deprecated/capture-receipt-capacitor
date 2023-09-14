/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


struct RspSurveyQuestion : Rsp {
    private let text: String?
    private let type: BRSurveyQuestionType
    private let answers: [BRSurveyAnswer]
    private let multipleAnswers: Bool
    private let userResponse: BRSurveyResponse
    
    init (surveyQuestion: BRSurveyQuestion) {
        text = surveyQuestion.text
        type = surveyQuestion.type
        answers = surveyQuestion.answers
        multipleAnswers = surveyQuestion.multipleAnswers
        userResponse = surveyQuestion.userResponse
    }
    
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["text"] = text
        ret["type"] = Int(type.rawValue)
        ret["answers"] = JSArray(answers.map { answer in answers })
        ret["multipleAnswers"] = multipleAnswers
        ret["userResponse"] = JSObject(_immutableCocoaDictionary: userResponse)
        return ret
    }
}
