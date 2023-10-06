/*
 * RspAccount Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor


public class RspAccount : Rsp{
    
    /// The username associated with the account.
    private let username: String
    
    /// The source or type of the account.
    private let source: String
    
    /// A boolean flag indicating whether the account is verified.
    private let isVerified: Bool
    
    /**
     Initializes an `RspAccount` object with the provided account details.
     
     - Parameter requestId: The unique identifier for the associated request.
     - Parameter account: An `Account` object containing the account information.
     */
    public init(requestId: String, account: Account) {
        self.username = account.user
        self.source = account.accountType.source
        self.isVerified = account.isVerified ?? false
        super.init(requestId: requestId, event: .onAccount)
    }
    
    /**
     Converts the `RspAccount` object into a dictionary suitable for use in plugin response data.
     
     - Returns: A dictionary representing the account data in a format suitable for a Capacitor plugin call result.
     */
    override func toPluginCallResultData() -> PluginCallResultData {
        var ret = super.toPluginCallResultData()
        ret["payload"] = [
            "username" : username,
            "id" : source,
            "verified" : isVerified
        ]
        return ret
    }
}
