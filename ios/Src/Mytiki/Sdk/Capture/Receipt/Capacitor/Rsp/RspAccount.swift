/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspAccount : Rsp {
    private let username: String
    private let provider: String
    private let verified: Bool
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", username)
        JSObject.updateValue("provider", provider)
        JSObject.updateValue("verified", verified)
    }
}
