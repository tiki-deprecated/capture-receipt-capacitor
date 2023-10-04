/*
 * ReqAccount Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A Swift class for handling account-related request data in a Capacitor plugin.
public class ReqAccount : Req {
    /// The common account information.
    let accountCommon: AccountCommon
    /// The username associated with the account.
    let username: String
    /// The password associated with the account (optional).
    let password: String?
    /// Whether the account is verified (optional).
    let isVerified: Bool?
    
    /// Initializes the ReqAccount class with data from a CAPPluginCall.
    ///
    /// - Parameter call: The CAPPluginCall object representing the request.
    /// - Throws: An error if required data is missing.
    override init(_ call: CAPPluginCall) throws {
        if(AccountCommon.defaults[call.getString("id") ?? ""] == nil){
            accountCommon = AccountCommon(type: .none, source: "")
        }else{
            accountCommon = AccountCommon.defaults[call.getString("id") ?? ""]!
        }
        username = call.getString("username") ?? ""
        password = call.getString("password")
        isVerified = call.getBool("isVerified")
        try super.init(call)
    }
    
    /// Constructs an Account object using the gathered data.
    ///
    /// - Returns: An Account object created from the collected data.
    public func account() -> Account{
        return Account(accountType: self.accountCommon, user: self.username, password: self.password, isVerified: self.isVerified)
    }
}
