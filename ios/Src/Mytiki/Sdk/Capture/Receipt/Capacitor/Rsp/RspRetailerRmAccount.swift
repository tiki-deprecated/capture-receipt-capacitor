//
/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspRetailerRmAccount : Rsp  {
    private let account: Account
    private let isRemoved: Bool
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", account.credentials.username())
        JSObject.updateValue("retailer", RetailerEnum.fromInt(account.retailerId).toString())
        JSObject.updateValue("isRemoved", isRemoved)
    }
}
