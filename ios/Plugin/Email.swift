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
    
    let defaults = UserDefaults.standard
    
    /// Initializes the Email plugin with license and product keys and optional Google Client ID.
    ///
    /// - Parameters:
    ///   - licenseKey: The license key for the plugin.
    ///   - productKey: The product key for the plugin.
    ///   - googleClientId: The Google Client ID for OAuth authentication (optional).
    public init(_ licenseKey: String, _ productKey: String, _ googleClientId: String?)  {
        DispatchQueue.main.async {
            BRScanManager.shared().licenseKey = licenseKey
            BRScanManager.shared().prodIntelKey = productKey
            BREReceiptManager.shared().googleClientId = googleClientId
            BRScanManager.shared().daysToStoreReceiptData =  30
            BRAccountLinkingManager.shared()
        }
        
    }
    
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
    public func login(_ account: Account, _ pluginCall: CAPPluginCall) {
        let email = BRIMAPAccount(provider: .gmailIMAP, email: account.user, password: account.password!)
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        Task(priority: .high) {
            await BREReceiptManager.shared().setupIMAP(for: email, viewController: rootVc!, withCompletion: { result in
            })
            await BREReceiptManager.shared().verifyImapAccount(email, withCompletion: { success, error in
                if success {
                    pluginCall.resolve()
                } else {
                    pluginCall.reject(error.debugDescription)
                }
            })
    }
    }
    
    /// Logs out a user account or signs out of all accounts.
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    ///   - account: An optional instance of the Account struct containing user and account information.
    public func logout(_ pluginCall: CAPPluginCall, _ account: Account?){
        if(account != nil ){
            let email = BRIMAPAccount(provider: .gmailIMAP, email: account?.user ?? "", password: account?.password ?? "")
            BREReceiptManager.shared().signOut(from: email) { error in
                if(error != nil){
                    pluginCall.reject(error?.localizedDescription ?? "Email logout error.")
                }else{
                    pluginCall.resolve()
                }
            }
        }else{
            BREReceiptManager.shared().signOut(completion: { error in
                if(error == nil){
                    pluginCall.reject(error?.localizedDescription ?? "Email logout error.")
                }else{
                    pluginCall.resolve()
                }
            })
        }
    }
    
    /// Retrieves e-receipts for a user account or initiates OAuth authentication for scanning OAuth.
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    ///   - account: An optional instance of the Account struct containing user and account information.
    public func scan(_ pluginCall: CAPPluginCall, _ account: Account?){
        let dayCutOffSaved = defaults.object(forKey: "lasIMAPScan") as! Date
        let timeInterval = dayCutOffSaved.timeIntervalSinceNow
        let diference = Int(timeInterval) / 86400
        if(diference > 15) {
            BREReceiptManager.shared().dayCutoff = 15

        }else{
            BREReceiptManager.shared().dayCutoff = diference
        }
        if(account != nil){
            let provider = EmailEnum(rawValue:  account!.accountType.source)?.toBREReceiptProvider()
            let email = BRIMAPAccount(provider: provider!, email: account!.user, password: account!.password!)
            BREReceiptManager.shared().getEReceipts(for: email){scanResults, emailAccount, error in
                if(scanResults != nil){
                    scanResults?.forEach{scanResults in
                        pluginCall.resolve(
                            RspScan(scan: RspReceipt(scanResults: scanResults),
                                    account: Account(provider: emailAccount!.provider, email: emailAccount!.email))
                            .toPluginCallResultData()
                        )
                    }
                }else{
                    pluginCall.reject(error?.localizedDescription ?? "No receipts.")
                }
                self.defaults.setValue(Date(), forKey: "lastIMAPScan")
            }
        }else{
            Task(priority: .high){
                BREReceiptManager.shared().getEReceipts(){scanResults, emailAccount, error in
                    if(scanResults != nil){
                        scanResults?.forEach{scanResults in
                            pluginCall.resolve(
                                RspScan(scan: RspReceipt(scanResults: scanResults),
                                        account: Account(provider: emailAccount!.provider, email: emailAccount!.email))
                                .toPluginCallResultData()
                            )
                        }
                    }else{
                        pluginCall.reject(error?.localizedDescription ?? "No receipts.")
                    }
                    self.defaults.setValue(Date(), forKey: "lastIMAPScan")
                }
            }
        }
    }
    
    /// Retrieves a list of linked email accounts.
    ///
    /// - Returns: An array of Account objects representing linked email accounts.
    public func accounts() -> [Account]{
        var verifiedAccounts: [Account] = []
        let linkedAccounts = BREReceiptManager.shared().getLinkedAccounts()
        linkedAccounts?.forEach{ brAccount in
            let account = Account(provider: brAccount.provider, email:brAccount.email)
            account.isVerified = true
            verifiedAccounts.append(account)
        }
        return verifiedAccounts
    }
    /// Logs in a user account using the OAuth authentication
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    func loginOauth(_ pluginCall: CAPPluginCall){
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        DispatchQueue.main.async {
            BREReceiptManager.shared().beginOAuth(for: .gmail, with: rootVc!, andCompletion: { error in
                if error == nil {
                    // Account successfully authenticated
                    for account in self.accounts() {
                        if(account.accountType.source == "GMAIL"){
                            pluginCall.resolve(account.toResultData())
                            return
                        }
                    }
                    pluginCall.reject("Account not saved")
                }else{
                    // Account problem
                    pluginCall.reject(error.debugDescription)
                }
            })
        }
    }
    
    /// Retrieves e-receipts for a user account OAuth authentication for scanning.
    ///
    /// - Parameters:
    ///   - pluginCall: The CAPPluginCall object representing the plugin call.
    func scanOauth(_ pluginCall: CAPPluginCall){
        Task(priority: .high) {
            await BREReceiptManager.shared().getEReceipts(completion: {scanResults, emailAccount, error in
                if(scanResults != nil){
                    scanResults?.forEach{scanResults in
                        pluginCall.resolve(
                            RspScan(scan: RspReceipt(scanResults: scanResults),
                                    account: Account(provider: emailAccount!.provider, email: emailAccount!.email))
                            .toPluginCallResultData()
                        )
                    }
                }else{
                    print(error?.localizedDescription ?? "No receipts.")
                }
            })
        }
    }

    
    
}
