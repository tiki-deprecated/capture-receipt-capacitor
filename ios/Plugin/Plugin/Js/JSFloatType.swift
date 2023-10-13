/*
 * RspFloatType Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing floating-point values with confidence scores for the ReceiptCapture plugin.

 This struct is used to encapsulate floating-point values along with their confidence scores in response to plugin calls.
 */
struct JSFloatType{
    /// The confidence score associated with the value.
    private let confidence: Float?
    
    /// The floating-point value.
    private let value: Float

    /**
     Initializes an `RspFloatType` struct based on a `BRFloatValue` object.

     - Parameter floatType: A `BRFloatValue` object containing the floating-point value and its confidence score.
     */
    init(floatType: BRFloatValue) {
        confidence = floatType.confidence
        value = floatType.value
    }
    
    init (float: Float) {
        value = float
        confidence = nil
    }

    /**
     Converts the `RspFloatType` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary representing floating-point values and their confidence scores in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
        var result = JSObject()
        result["confidence"] = confidence
        result["value"] = value
        return result
    }
}

