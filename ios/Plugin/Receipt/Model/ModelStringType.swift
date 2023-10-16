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
struct ModelStringType{
    /// The confidence level of the string value.
    private let confidence: Float?
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
    
    init(string: String) {
        value = string
        confidence = nil
    }
    
    static func opt(stringType: BRStringValue?) -> JSStringType? {
        return stringType != nil ? JSStringType(stringType: stringType!) : nil
    }
    
    static func opt(string: String?) -> JSStringType? {
        return string != nil ? JSStringType(string: string!) : nil
    }
    
    /**
     Converts the `RspStringType` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the string value and its confidence level in a format suitable for a Capacitor plugin call result.
     */
    public func toJSObject() -> JSObject {
        var result = JSObject()
        result["confidence"] = confidence
        result["value"] = value
        return result;
    }
}

