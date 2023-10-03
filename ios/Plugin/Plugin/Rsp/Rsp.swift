/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor
import Foundation

public class Rsp {
    let requestId: String
    let event: PluginEvent
    
    init(requestId: String, event: PluginEvent) {
        self.requestId = requestId
        self.event = event
    }
    
    func toPluginCallResultData() -> [String: Any] {
        return [
            "requestId": requestId,
            "event": event
        ]
    }
}

