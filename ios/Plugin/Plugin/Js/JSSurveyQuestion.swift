/*
 * RspSurveyQuestion Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


/**
 Represents a response containing survey question details.

 This struct is used to convey survey question details including text, type, answers, multipleAnswers flag, and user response.
 */
struct JSSurveyQuestion{
    /// The text of the survey question.
    private let text: String?
    /// The type of the survey question.
    private let type: String?
    /// The list of survey answers associated with the question.
    private let answers: [JSSurveyAnswer]
    /// A boolean flag indicating whether multiple answers are allowed.
    private let multipleAnswers: Bool
    /// The user's response to the survey question.
    private let userResponse: JSSurveyResponse

    /**
     Initializes an `RspSurveyQuestion` struct.

     - Parameters:
        - surveyQuestion: The survey question containing text, type, answers, multipleAnswers flag, and user response.
     */
    init(surveyQuestion: BRSurveyQuestion) {
        text = surveyQuestion.text
        type = String(describing: surveyQuestion.type)
        answers = surveyQuestion.answers.map { answer in JSSurveyAnswer(surveyAnswer: answer) }
        multipleAnswers = surveyQuestion.multipleAnswers
        userResponse = JSSurveyResponse(surveyResponse: surveyQuestion.userResponse)
    }

    /**
     Converts the `RspSurveyQuestion` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the survey question's text, type, answers, multipleAnswers flag, and user response in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
        var ret = JSObject()
        ret["text"] = text
        ret["type"] = type
        ret["answers"] = JSArray(answers.map { answer in answers })
        ret["multipleAnswers"] = multipleAnswers
        ret["userResponse"] = userResponse.toJSObject()
        return ret
    }
}

