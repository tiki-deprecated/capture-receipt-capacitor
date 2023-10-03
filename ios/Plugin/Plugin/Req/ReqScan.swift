/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */


import Foundation
import Capacitor

public struct ReqScan {
    let daysCutOff: Int?
    let requestId: String
    
    init(data: CAPPluginCall) {
        self.daysCutOff = data.getInt("dayCutOff", 7)
        self.requestId = data.getString("requestId", "")
    }
}
