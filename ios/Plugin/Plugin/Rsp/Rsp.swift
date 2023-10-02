/*
 * Rsp Protocol
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A protocol defining a common interface for response objects in the ReceiptCapture plugin.
public class Rsp {
    let requestId: String
    let event: PluginEvent
    
    init(requestId: String, event: PluginEvent){
        self.requestId = requestId
        self.event = event
    }
    
    /// Converts the response object into a `PluginCallResultData` dictionary for sending back to the Capacitor plugin.
    ///
    /// - Returns: A dictionary representing the response data in a format suitable for a Capacitor plugin call result.
    func toPluginCallResultData() -> PluginCallResultData {
        return [
            "requestId" : requestId,
            "event" : event
        ]
    }
}
