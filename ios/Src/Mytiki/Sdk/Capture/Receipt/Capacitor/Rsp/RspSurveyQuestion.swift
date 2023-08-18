/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


struct RspSurveyQuestion : Rsp {
    private let myIndex: Int
    private let lastQuestion: Boolean
    private let nextQuestionIndex: Int
    private let serverId: Int
    private let text: String?
    private let type: String?
    private let answers: List<RspSurveyAnswer>
    private let multipleAnswers: Boolean
    private let totalNumberOfQuestions: Int
    
    init (surveyQuestion: SurveyQuestion) {
        myIndex = surveyQuestion.myIndex()
        lastQuestion = surveyQuestion.lastQuestion()
        nextQuestionIndex = surveyQuestion.nextQuestionIndex()
        serverId = surveyQuestion.serverId()
        text = surveyQuestion.text()
        type = surveyQuestion.type()?.a
        answers = surveyQuestion.answers()?.map { answer -> RspSurveyAnswer(answer) } ?? emptyList()
        multipleAnswers = surveyQuestion.multipleAnswers()
        totalNumberOfQuestions = surveyQuestion.totalNumberOfQuestions()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("myIndex", myIndex)
        JSObject.updateValue("lastQuestion", lastQuestion)
        JSObject.updateValue("nextQuestionIndex", nextQuestionIndex)
        JSObject.updateValue("serverId", serverId)
        JSObject.updateValue("text", text)
        JSObject.updateValue("type", type)
        JSObject.updateValue("answers", JSONArray(answers.map { answer -> answer.toJson() }))
        JSObject.updateValue("multipleAnswers", multipleAnswers)
        JSObject.updateValue("totalNumberOfQuestions", totalNumberOfQuestions)
    }
}
