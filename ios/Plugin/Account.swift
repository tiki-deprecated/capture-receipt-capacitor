/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


public class Account {
    let accountType: AccountCommon
    let user: String
    let password: String?
    var isVerified: Bool?
    
    init(accountType: AccountCommon, user: String, password: String?, isVerified: Bool?) {
        self.accountType = accountType
        self.user = user
        self.password = password
        self.isVerified = isVerified ?? false
    }
    
    func toResultData() -> PluginCallResultData {
        return [
            "username" : user,
            "source" : accountType.source,
            "verified" : isVerified ?? false
        ]
    }
}
