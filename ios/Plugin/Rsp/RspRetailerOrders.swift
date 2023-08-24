/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspRetailerOrders : Rsp {
    private let provider: String
    private let username: String
    private let scan: BRScanResults
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", username)
        JSObject.updateValue("provider", provider)
        JSObject.updateValue("scan",  RspScan(scan).toJson())
    }
}
