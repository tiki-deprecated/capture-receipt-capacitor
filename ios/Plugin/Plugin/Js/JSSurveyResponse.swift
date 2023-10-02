/*
 * RspSurveyResponse Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt


/**
 Represents a response containing survey response details.

 This struct is used to convey survey response details including selected answers and free text comments.
 */
struct JSSurveyResponse {
    /// The indices of selected answers in the survey.
    let answersSelected: [Int]
    /// Free-text comments provided as part of the survey response.
    let freeText: String

    /**
     Initializes an `RspSurveyResponse` struct.

     - Parameters:
        - answersSelected: The indices of selected answers in the survey.
        - freeText: Free-text comments provided as part of the survey response.
     */
    init(answersSelected: [Int], freeText: String) {
        self.answersSelected = answersSelected
        self.freeText = freeText
    }

    /**
     Converts the `RspSurveyResponse` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the selected answers' indices and free-text comments in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["answersSelected"] = JSArray(answersSelected.map { answerSelected in answersSelected })
        ret["freeText"] = freeText
        return ret
    }
}
