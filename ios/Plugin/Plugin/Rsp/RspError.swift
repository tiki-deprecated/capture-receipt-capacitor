/*
 * RspError Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Capacitor

/**
 A class representing an error response for the ReceiptCapture plugin.
 
 This class is used to encapsulate error information for use in plugin responses. It contains details such as an error message and an error code enumeration.
 
 - Note: This class is typically used to construct response data for error-related plugin calls.
 */
public class RspError: Rsp {
    /// The error message associated with the response.
    let message: String
    
    /// The error code associated with the response.
    let code: RspErrorEnum
    
    /**
     Initializes an `RspError` object with the provided error details.
     
     - Parameter requestId: The unique identifier for the associated request.
     - Parameter message: The error message describing the issue.
     - Parameter code: The error code enumeration (default is `.ERROR`).
     */
    init(requestId: String, message: String, code: RspErrorEnum = .ERROR) {
        self.message = message
        self.code = code
        super.init(requestId: requestId, event: PluginEvent.onError)
    }
    
    /**
     Converts the `RspError` object into a dictionary suitable for use in plugin response data.
     
     - Returns: A dictionary representing the error data in a format suitable for a Capacitor plugin call result.
     */
    override func toPluginCallResultData() -> PluginCallResultData {
        var ret = super.toPluginCallResultData()
        ret["payload"] = [
            "message": message,
            "code": code.rawValue
        ]
        return ret
    }
}
