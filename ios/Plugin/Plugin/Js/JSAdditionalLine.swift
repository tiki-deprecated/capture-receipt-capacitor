/*
 * RspAdditionalLine Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

/**
 Represents a response containing additional line information for a product in the ReceiptCapture plugin.

 This class is used to encapsulate additional line details of a product, including its type, text, and line number in response to plugin calls.
 */
class JSAdditionalLine {
    /// The type of the additional line (e.g., "Subtotal").
    private let type: JSStringType?
    
    /// The text content of the additional line.
    private let text: JSStringType?
    
    /// The line number representing the order of the additional line.
    private let lineNumber: Int

    /**
     Initializes an `RspAdditionalLine` instance based on a `BRProductAdditionalLine` object.

     - Parameter additionalLine: A `BRProductAdditionalLine` object containing additional line information.
     */
    init(additionalLine: BRProductAdditionalLine) {
        type = JSStringType(stringType: additionalLine.type)
        text = JSStringType(stringType: additionalLine.text)
        lineNumber = additionalLine.lineNumber
    }

    /**
     Converts the `RspAdditionalLine` object into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary representing the additional line's type, text, and line number in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
        var result = JSObject()
        result["type"] = type?.toJSObject()
        result["text"] = text?.toJSObject()
        result["lineNumber"] = lineNumber
        return result
    }
}
