//
/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


struct RspRetailerAccount : Rsp{
    var account: RspAccount
    var isVerified: Bool
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", account.credentials.username())
        JSObject.updateValue("retailer", RetailerEnum.fromInt(account.retailerId).toString())
        JSObject.updateValue("isVerified", isVerified)
    }
}
