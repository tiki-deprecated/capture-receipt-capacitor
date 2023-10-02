/*
 * Email Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/// A Swift class representing an email plugin for handling e-receipts and email account management.
public class Email {
    
    /// Initializes the Email plugin with license and product keys.
    ///
    /// - Parameters:
    ///   - licenseKey: The license key for the plugin.
    ///   - productKey: The product key for the plugin.
    public init(_ licenseKey: String, _ productKey: String){
        DispatchQueue.main.async {
            BRScanManager.shared().licenseKey = licenseKey
            BRScanManager.shared().prodIntelKey = productKey
            BREReceiptManager.shared().dayCutoff = 7
            BRScanManager.shared().daysToStoreReceiptData =  30
            BRAccountLinkingManager.shared()
        }
    }
    
    /// Logs in a user account using the provided credentials or initiates OAuth authentication for Gmail.
    ///
    /// - Parameters:
    ///   - account: An instance of the Account struct containing user and account information.
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    public func login(_ account: Account, onError: @escaping (String) -> Void, onComplete: @escaping () -> Void)  {
        let email = BRIMAPAccount(provider: .gmailIMAP, email: account.user, password: account.password!)

        DispatchQueue.main.async {
            let rootVc = UIApplication.shared.windows.first?.rootViewController
            BREReceiptManager.shared().setupIMAP(for: email, viewController: rootVc!, withCompletion: { result in
            })
            BREReceiptManager.shared().verifyImapAccount(email, withCompletion: { success, error in
                if !success {
                    onComplete()
                } else {
                    onError(error.debugDescription)
                }
            })
        }

    }
    
    /// Logs out a user account or signs out of all accounts.
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    ///   - account: An optional instance of the Account struct containing user and account information.
    public func logout(reqAccount: ReqAccount,  onError: @escaping (String) -> Void, onComplete: @escaping () -> Void){
        if(reqAccount.accountCommon.source != ""){
            let email = BRIMAPAccount(provider: .gmailIMAP, email: reqAccount.username, password: reqAccount.password ?? "")
            BREReceiptManager.shared().signOut(from: email) { error in
                if(error != nil){
                    onError(error?.localizedDescription ?? "Email logout error.")
                }else{
                    onComplete()
                }
            }
        }else{
            BREReceiptManager.shared().signOut(completion: { error in
                if(error == nil){
                    onError(error?.localizedDescription ?? "Email logout error.")
                }else{
                    onComplete()
                }
            })
        }
    }
    
    /// Retrieves e-receipts for a user account or initiates OAuth authentication for scanning OAuth.
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    ///   - account: An optional instance of the Account struct containing user and account information.
    public func scan(reqScan: ReqScan, onError: @escaping (String) -> Void, onReceipt: @escaping (RspReceipt) -> Void, onComplete: @escaping () -> Void){
        BREReceiptManager.shared().dayCutoff = reqScan.daysCutOff ?? 7
        Task(priority: .high){
            BREReceiptManager.shared().getEReceipts(){scanResults, emailAccount, error in
                if(scanResults != nil){
                    scanResults?.forEach{scanResults in
                        onReceipt(RspReceipt(requestId: reqScan.requestId, scanResults: scanResults))
                    }
                    onComplete()
                }else{
                    onError(error?.localizedDescription ?? "No receipts.")
                }
            }
            
        }
    }
    
    /// Retrieves a list of linked email accounts.
    ///
    /// - Returns: An array of Account objects representing linked email accounts.
    public func accounts(onError: (String) -> Void, onAccount: (Account) -> Void,  onComplete: () -> Void) {
        let linkedAccounts = BREReceiptManager.shared().getLinkedAccounts()
        linkedAccounts?.forEach{ brAccount in
            let account = Account(provider: brAccount.provider, email:brAccount.email)
            account.isVerified = true
            onAccount(account)
        }
        onComplete()
    }


    
    
}
