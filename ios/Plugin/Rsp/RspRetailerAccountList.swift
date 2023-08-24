/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspRetailerAccountList : Rsp{
    private let accounts: Array<RspRetailerAccount>
    
    func toJson() -> JSObject {
        JSObject.updateValue("accounts", JSONArray(accounts.map { account -> account.toJson() }))

    }
}
