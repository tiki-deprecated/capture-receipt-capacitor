/*
 * Req Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A Swift class for handling request data in a Capacitor plugin.
public class Req {
    let requestId: String

    
    /// Initializes the Req class with the request identifier from a CAPPluginCall.
    ///
    /// - Parameter call: The CAPPluginCall object representing the request.
    /// - Throws: An error if the request identifier is missing.
    init(_ call: CAPPluginCall) throws {
        guard let reqId = call.getString("requestId") else {
            call.reject("Add a requestId in the call.")
            throw NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Add a requestId in the call."])
        }
        requestId = reqId
    }
}
