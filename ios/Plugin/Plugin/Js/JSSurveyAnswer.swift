/*
 * RspSurveyAnswer Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing survey answer details.

 This struct is used to convey survey answer details including text and description.
 */
struct JSSurveyAnswer {
    /// The text of the survey answer.
    private let text: String?
    /// The description of the survey answer.
    private let description: String?

    /**
     Initializes an `RspSurveyAnswer` struct.

     - Parameters:
        - surveyAnswer: The survey answer containing text and description.
     */
    init(surveyAnswer: BRSurveyAnswer) {
        text = surveyAnswer.text
        description = surveyAnswer.description
    }

    /**
     Converts the `RspSurveyAnswer` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the survey answer's text and description in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "text": text,
            "description": description
        ]
    }
}
