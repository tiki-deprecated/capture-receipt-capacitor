/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

class Req {
    let requestId: String

    init(call: CAPPluginCall) throws {
        guard let reqId = call.getString("requestId") else {
            call.reject("Add a requestId in the call.")
            throw NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Add a requestId in the call."])
        }
        requestId = reqId
    }
}
