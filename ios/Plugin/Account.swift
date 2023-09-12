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
    
    init(provider: BREReceiptProvider, email: String, password: String? = nil){
        let emailEnum = EmailEnum.allCases.first(where: {value in
            value.toBREReceiptProvider() == provider
        })
        self.accountType = AccountCommon.defaults.first(where: {key, value in
            value.source == emailEnum!.rawValue
        })!.value
        self.user = email
        self.password = password
    }
    
    func toResultData() -> PluginCallResultData {
        return [
            "username" : user,
            "source" : accountType.source,
            "verified" : isVerified ?? false
        ]
    }
}
