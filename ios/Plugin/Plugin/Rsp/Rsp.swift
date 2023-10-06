/*
 * Rsp Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor
import Foundation

/// A class representing a response structure for a Capacitor plugin.
public class Rsp {
    /// The unique identifier for the request associated with this response.
    let requestId: String
    
    /// The event type associated with this callback response event.
    let event: PluginEvent
    
    
    /// Initializes the Rsp class with the provided request identifier and event.
    ///
    /// - Parameters:
    ///   - requestId: The unique identifier for the associated request.
    ///   - event: The event type associated with this response.
    init(requestId: String, event: PluginEvent) {
        self.requestId = requestId
        self.event = event
    }
    
    /// Converts the response data to a format suitable for a Capacitor plugin call result.
    ///
    /// - Returns: A dictionary containing the response data.
    func toPluginCallResultData() -> [String: Any] {
        return [
            "requestId": requestId,
            "event": event.rawValue
        ]
    }
}

