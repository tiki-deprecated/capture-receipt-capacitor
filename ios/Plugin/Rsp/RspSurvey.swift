/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
//
//import Foundation
//import BlinkReceipt
//import BlinkEReceipt
//import Capacitor
//
//struct RspSurvey : Rsp {
//    private let clientUserId: String?
//    private let serverId: Int
//    private let slug: String?
//    private let rewardValue: String?
//    private let startDate: String?
//    private let endDate: String?
//    private let questions: [RspSurveyQuestion]
//    
//    init (survey: BRSurvey){
//        clientUserId = survey.c
//        serverId = survey.serverId()
//        slug = survey.slug()
//        rewardValue = survey.rewardValue()
//        startDate = survey.startDate()
//        endDate = survey.endDate()
//        questions =
//            survey.questions()?.map { question -> RspSurveyQuestion(question) } ?? emptyList()
//    }
//    
//    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
//        var ret = JSObject()
//        ret["clientUserId"] = clientUserId
//        ret["serverId"] = serverId
//        ret["slug"] = slug
//        ret["rewardValue"] = rewardValue
//        ret["startDate"] = startDate
//        ret["endDate"] = endDate
//        ret["questions"] = questions
//        return ret
//    }
//}
