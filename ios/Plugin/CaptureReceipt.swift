/*
 * ReceiptCapture Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt

/// A Swift class responsible for receipt capture and management.
public class CaptureReceipt: NSObject {
    
    var email: Email? = nil
    var retailer: Retailer? = nil
    
    /// The pending CAPPluginCall to handle asynchronous scanning.
    public static var pendingScanCall: CAPPluginCall?
    
    /// Initializes the ReceiptCapture class.
    ///
    /// - Parameter call: The CAPPluginCall representing the initialization request.
    public func initialize(reqInitialize: ReqInitialize) {
        let licenseKey = reqInitialize.licenseKey
        let productKey = reqInitialize.productKey
        let scanManager = BRScanManager.shared()
        scanManager.licenseKey = licenseKey
        scanManager.prodIntelKey = productKey
        email = Email(licenseKey, productKey)
        retailer = Retailer(licenseKey, productKey)
    }
    
    /// Handles user login for receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the login request.
    public func login(reqAccount: ReqAccount, onError: @escaping (String) -> Void, onComplete: @escaping (Account?) -> Void) {
        guard let accountType = AccountCommon.defaults[reqAccount.accountCommon.source] else {
            onError("Invalid source: \(reqAccount.accountCommon.source)")
            return
        }
        let account = Account.init(accountType: accountType, user: reqAccount.username, password: reqAccount.password, isVerified: false)
        switch account.accountType.type {
        case .email :
            guard let email = email else {
                onError("Email not initialized. Did you call .initialize()?")
                return
            }
            email.login(account, onError: {error in onError(error)}, onComplete: {onComplete(account)})
            break
        case .retailer :
            guard let retailer = retailer else {
                onError("Retailer not initialized. Did you call .initialize()?")
                return
            }
            retailer.login(account, onError: {error in onError(error)}, onComplete: {account in onComplete(account)})
            break
        case .none :
            onError("Invalid Type")
        }

    }
    /// Handles user logout from receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the logout request.
    public func logout(reqAccount: ReqAccount, onError: @escaping (String) -> Void, onComplete: @escaping () -> Void) {
        if(reqAccount.accountCommon.type == .none){
            if(reqAccount.username != "" || reqAccount.password != nil){
                onError("Error: Invalid logout arguments. If you want delete all accounts, don't send username of password")
                return
            }else{
                guard let retailer = retailer else {
                    onError("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                guard let email = email else {
                    onError("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.logout(reqAccount: reqAccount, onError: {error in onError(error)}, onComplete: {onComplete()})
                retailer.logout(reqAccount: reqAccount, onError: {error in onError(error)}, onComplete: {onComplete()})
                return
            }

        }
        guard let accountType = AccountCommon.defaults[reqAccount.accountCommon.source] else {
            onError("Invalid source: \(reqAccount.accountCommon.source)")
            return
        }
        let account = Account.init(accountType: AccountCommon.defaults[reqAccount.accountCommon.source]!, user: reqAccount.username, password: reqAccount.password, isVerified: false)
        switch account.accountType.type {
        case .email :
            guard let email = email else {
                onError("Email not initialized. Did you call .initialize()?")
                return
            }
            email.logout(reqAccount: reqAccount, onError: {error in onError(error)}, onComplete: {onComplete()})
            break
        case .retailer :
            guard let retailer = retailer else {
                onError("Retailer not initialized. Did you call .initialize()?")
                return
            }
            retailer.logout(reqAccount: reqAccount, onError: {error in onError(error)}, onComplete: {onComplete()})
            break
        case .none :
            onError("Invalid Type")
        }
    }
    /// Retrieves a list of user accounts for receipt management.
     ///
     /// - Parameter call: The CAPPluginCall representing the request for account information.
    public func accounts( onError: @escaping (String) -> Void, onComplete: @escaping () -> Void, onAccount: (Account) -> Void) {
        guard let retailer = retailer else {
            onError("Retailer not initialized. Did you call .initialize()?")
            return
        }
        guard let email = email else {
            onError("Retailer not initialized. Did you call .initialize()?")
            return
        }
        
        email.accounts(onError: {error in onError(error)}, onAccount: {account in onAccount(account)}, onComplete: {onComplete()})
        retailer.accounts(onError: {error in onError(error)}, onAccount: {account in onAccount(account)}, onComplete: {onComplete()})
        onComplete()
        
    }
    
    /// Initiates receipt scanning based on the specified account type.
    ///
    /// - Parameter call: The CAPPluginCall representing the scan request.
    public func scan(call: CAPPluginCall?, reqScan: ReqScan, onError: @escaping (String) -> Void, onReceipt: @escaping (BRScanResults) -> Void, onComplete: @escaping () -> Void) {
            guard let retailer = retailer else {
                onError("Retailer not initialized. Did you call .initialize()?")
                return
            }
            guard let email = email else {
                onError("Email not initialized. Did you call .initialize()?")
                return
            }
        let reqScan = ReqScan(data: call!)
        email.scan(reqScan: reqScan, onError: {error in onError(error)}, onReceipt: {scanResult in onReceipt(scanResult)}, onComplete: {})
        retailer.orders(onError: {error in onError(error)}, onReceipt: {scanResult in onReceipt(scanResult)}, onComplete: {})

    }
    
}
