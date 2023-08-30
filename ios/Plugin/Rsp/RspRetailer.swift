/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspRetailer : Rsp{
    private let id: Int
    private let bannerId: Int
    
    init (retailerId: Retailer) {
        id = retailer.id
        bannerId = retailer.bannerId
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "id" : id,
            "bannerId" : bannerId
        ]
    }
}
