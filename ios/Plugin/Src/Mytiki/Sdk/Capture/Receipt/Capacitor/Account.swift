/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


public class Account {
    let accountType: AccountType
    let user: String
    let password: String?
    let isVerified: Bool?
    
    init(accountType: AccountType, user: String, password: String?, isVerified: Bool?) {
        self.accountType = accountType
        self.user = user
        self.password = password
        self.isVerified = isVerified
    }
    
    init(fromCall: CAPPluginCall) {
        super(
            accountType: AccountType(type: fromCall.getString("type"), source: fromcall.getString(""))
        )
    }
}
