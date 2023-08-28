/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspAccount {
    private let username: String
    private let source: String
    private let isVerified: Bool
    
    public init(account: Account) {
        self.username = account.user
        self.source = account.accountType.source
        self.isVerified = account.isVerified ?? false
    }
    
    func toResultData() -> PluginCallResultData {
        return [
            "username" : username,
            "source" : source,
            "verified" : isVerified
        ]
    }
}

