/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor
import Foundation

public class RspError: Rsp {
    let message: String
    let code: RspErrorEnum
    
    init(requestId: String, message: String, code: RspErrorEnum = .ERROR) {
        self.message = message
        self.code = code
        super.init(requestId: requestId, event: PluginEvent.onError)
    }
    
    func toJS() -> PluginCallResultData {
        var ret = super.toPluginCallResultData()
        ret["payload"] = [
            "message": message,
            "code": code.rawValue
        ]
    }
}

