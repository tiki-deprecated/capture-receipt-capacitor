/*
 * RspStringType Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing a string value with confidence.

 This struct is used to convey a string value along with its confidence level.
 */
struct JSStringType{
    /// The confidence level of the string value.
    private let confidence: Float
    /// The string value.
    private let value: String?

    /**
     Initializes an `RspStringType` struct.

     - Parameters:
        - stringType: The string value with confidence information.
     */
    init(stringType: BRStringValue) {
        confidence = stringType.confidence
        value = stringType.value
    }

    /**
     Converts the `RspStringType` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the string value and its confidence level in a format suitable for a Capacitor plugin call result.
     */
    public func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "confidence": confidence,
            "value": value ?? ""
        ]
    }
}

