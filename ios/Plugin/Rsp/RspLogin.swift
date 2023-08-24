/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspLogin : Rsp {
    private let username: String
    private let provider: String
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", username)
        JSObject.updateValue("provider", provider)
    }
    
}
