/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

public class RspError: Rsp, Error {
    let message: String
    let code: RspErrorEnum
    
    init(requestId: String, message: String, code: RspErrorEnum = .ERROR) {
        self.message = message
        self.code = code
        super.init(requestId: requestId, event: PluginEvent.onError)
    }
    
    // Converts the RSP error data to a dictionary
    func toJS() -> [String: Any] {
        var ret = super.toPluginCallResultData()
        ret["payload"]: [
            "message": message,
            "code": code.rawValue
        ]
    }
}
