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
    /// - Parameters:
    ///   - licenseKey: The license key for BlinkReceipt.
    ///   - productKey: The product key for BlinkReceipt.
    public func initialize(licenseKey: String, productKey: String) {
        let scanManager = BRScanManager.shared()
        scanManager.licenseKey = licenseKey
        scanManager.prodIntelKey = productKey
        email = Email(licenseKey, productKey)
        retailer = Retailer(licenseKey, productKey)
    }
    
    /// Handles user login for receipt management.
    ///
    /// - Parameters:
    ///   - account: An instance of the Account struct containing user and account information.
    ///   - onError: A closure to handle error messages.
    ///   - onComplete: A closure to handle the completion of the login process.
    public func login(account: Account, onError: @escaping (String) -> Void, onComplete: @escaping (Account) -> Void) {
        DispatchQueue.main.async {
            switch account.accountType.type {
            case .email :
                guard let email = self.email else {
                    onError("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.login(account, onError: {error in onError(error)}, onSuccess: {onComplete(account)})
                break
            case .retailer :
                guard let retailer = self.retailer else {
                    onError("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                retailer.login(account, onError: {error in onError(error)}, onSuccess: {account in onComplete(account)})
                break
            case .none :
                onError("Invalid Type")
            }
        }
    }
    
    /// Handles user logout from receipt management.
    ///
    /// - Parameters:
    ///   - onError: A closure to handle error messages.
    ///   - onComplete: A closure to handle the completion of the logout process.
    ///   - account: An optional instance of the Account struct containing user and account information.
    public func logout(onError: @escaping (String) -> Void, onComplete: @escaping () -> Void, account: Account? = nil) {
        if(account != nil){
            switch(account?.accountType.type){
            case .email :
                guard let email = self.email else {
                    onError("Email not initialized. Did you call .initialize()?")
                    return
                }
                email.logout(onError: {error in onError(error)}, onComplete: {onComplete()}, account: account)
                break
            case .retailer :
                guard let retailer = self.retailer else {
                    onError("Retailer not initialized. Did you call .initialize()?")
                    return
                }
                retailer.logout(onError: {error in onError(error)}, onComplete: {onComplete()}, account: account)
                break
            default:
                onError("Invalid logout account type.")
            }
        }else{
            guard let retailer = self.retailer else {
                onError("Retailer not initialized. Did you call .initialize()?")
                return
            }
            guard let email = self.email else {
                onError("Email not initialized. Did you call .initialize()?")
                return
            }
            email.logout(onError: {error in onError(error)}, onComplete: {onComplete()})
            retailer.logout(onError: {error in onError(error)}, onComplete: {onComplete()})
        }
    }
    
    /// Retrieves a list of user accounts for receipt management.
    ///
    /// - Parameters:
    ///   - onError: A closure to handle error messages.
    ///   - onComplete: A closure to handle the completion of the account retrieval process.
    ///   - onAccount: A closure to handle individual account information.
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
    /// - Parameters:
    ///   - onError: A closure to handle error messages.
    ///   - onReceipt: A closure to handle individual receipt results.
    ///   - onComplete: A closure to handle the completion of the scanning process.
    public func scan(onError: @escaping (String) -> Void, onReceipt: @escaping (BRScanResults) -> Void, onComplete: @escaping () -> Void) {
            guard let retailer = retailer else {
                onError("Retailer not initialized. Did you call .initialize()?")
                return
            }
            guard let email = email else {
                onError("Email not initialized. Did you call .initialize()?")
                return
            }
        email.scan(onError: {error in onError(error)}, onReceipt: {scanResult in onReceipt(scanResult)}, onComplete: {})
        retailer.orders(onError: {error in onError(error)}, onReceipt: {scanResult in onReceipt(scanResult)}, onComplete: {})

    }
    
}
