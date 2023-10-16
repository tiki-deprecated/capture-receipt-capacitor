//
//  JSSurvey.swift
//  Plugin
//
//  Created by Michael Audi on 10/13/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


/**
 Represents a response containing survey information.

 This struct is used to convey details about a survey, including its dates, questions, and rewards.
 */
struct ModelSurvey {
    /// The slug associated with the survey.
    private let slug: String?
    /// The reward value associated with the survey (float formated to 2 decimals)
    private let rewardValue: String?
    /// The start date of the survey (ISO8601).
    private let startDate: String?
    /// The end date of the survey (ISO8601).
    private let endDate: String?
    /// The list of survey questions.
    private let questions: [ModelSurveyQuestion]

    /**
     Initializes an `RspShipment` struct.

     - Parameters:
        - shipment: The shipment information.
     */
    init(survey: BRSurvey) {
        slug = survey.slug
        rewardValue = String(format:"%.2f", survey.rewardValue)
        startDate = survey.startDate != nil ? survey.startDate!.ISOStringFromDate() : nil
        endDate = survey.endDate != nil ? survey.endDate!.ISOStringFromDate() : nil
        questions = survey.questions.map { question in ModelSurveyQuestion(surveyQuestion: question) }
    }
    
    static func opt(survey: BRSurvey?) -> JSSurvey? {
        return survey != nil ? JSSurvey(survey: survey!) : nil
    }

    /**
     Converts the `JSSurvey` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing shipment information in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
        var ret = JSObject()
        ret["slug"] = slug
        ret["rewardValue"] = rewardValue
        ret["startDate"] = startDate
        ret["endDate"] = endDate
        ret["questions"] = JSArray(arrayLiteral: questions.map { question in question.toJSObject() })
        return ret
    }
}
