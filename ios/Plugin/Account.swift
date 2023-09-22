/*
 * Account Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */


import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/// A Swift class representing an account, which can be of different types, such as email or retailer.
public class Account {
    /// The type of the account, such as email or retailer, represented by an AccountCommon object.
    let accountType: AccountCommon
    /// The username associated with the account.
    let user: String
    /// The password associated with the account (optional).
    let password: String?
    /// A flag indicating whether the account has been verified (optional).
    var isVerified: Bool?
    
    /// Initializes an Account object with the specified properties.
    ///
    /// - Parameters:
    ///   - accountType: An AccountCommon object representing the type and source of the account.
    ///   - user: The username associated with the account.
    ///   - password: The password associated with the account (optional).
    ///   - isVerified: A flag indicating whether the account has been verified (optional).
    init(accountType: AccountCommon, user: String, password: String?, isVerified: Bool?) {
        self.accountType = accountType
        self.user = user
        self.password = password
        self.isVerified = isVerified ?? false
    }
    
    /// Initializes an Account object for an email account with the specified provider and email address.
    ///
    /// - Parameters:
    ///   - provider: The BREReceiptProvider representing the email provider.
    ///   - email: The email address associated with the account.
    ///   - password: The password associated with the account (optional).
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
    /// Initializes an Account object for a retailer account with the specified retailer name, username, and password.
    ///
    /// - Parameters:
    ///   - retailer: The name of the retailer.
    ///   - username: The username associated with the retailer account.
    ///   - password: The password associated with the retailer account.
    init(retailer: String, username: String, password: String){
        self.accountType = AccountCommon(type: .retailer, source: retailer)
        self.user = username
        self.password = password
        self.isVerified = false
    }
    
    /// Converts the Account object to a PluginCallResultData dictionary for use in plugin calls.
    ///
    /// - Returns: A PluginCallResultData dictionary representing the Account object's properties.
    func toResultData() -> PluginCallResultData {
        return [
            "username" : user,
            "source" : accountType.source,
            "verified" : isVerified ?? false
        ]
    }
    
    /// Converts the Account object to a PluginCallResultData dictionary for use in plugin calls.
    ///
    /// - Returns: A PluginCallResultData dictionary representing the Account object's properties.
    func toString() -> [String : Any] {
        return [
            "username" : self.user,
            "source" : self.accountType.source,
            "verified" : self.isVerified ?? false
        ]
    }
}
