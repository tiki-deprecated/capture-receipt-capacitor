/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspSurvey : Rsp {
    private let clientUserId: String?
    private let serverId: Int
    private let slug: String?
    private let rewardValue: String?
    private let startDate: String?
    private let endDate: String?
    private let questions: List<RspSurveyQuestion>
    
    init (survey: Survey){
        clientUserId = survey.clientUserId()
        serverId = survey.serverId()
        slug = survey.slug()
        rewardValue = survey.rewardValue()
        startDate = survey.startDate()
        endDate = survey.endDate()
        questions =
            survey.questions()?.map { question -> RspSurveyQuestion(question) } ?? emptyList()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("clientUserId", clientUserId)
        JSObject.updateValue("serverId", serverId)
        JSObject.updateValue("slug", slug)
        JSObject.updateValue("rewardValue", rewardValue)
        JSObject.updateValue("startDate", startDate)
        JSObject.updateValue("endDate", endDate)
        JSObject.updateValue("questions", JSONArray(questions.map { question -> question.toJson() }))
    }
}
