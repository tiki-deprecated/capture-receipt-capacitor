/*
 * Retailer Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/// A Swift class representing a retailer plugin for use with Capacitor.
public class Retailer : CAPPlugin{
    
    /// Initializes the Retailer plugin with license and product keys.
    ///
    /// - Parameters:
    ///   - licenseKey: The license key for the plugin.
    ///   - productKey: The product key for the plugin.
    public init(_ licenseKey: String, _ productKey: String){
        DispatchQueue.main.async{
            BRScanManager.shared().licenseKey = licenseKey
            BRScanManager.shared().prodIntelKey = productKey
            BRAccountLinkingManager.shared()
        }
        super.init()
    }
    /// Default initializer for the Retailer plugin.
    public override init() {
        super.init()
    }
    /// Logs in a user account with the specified credentials.
    ///
    /// - Parameters:
    ///   - account: An instance of the Account struct containing user and account information.
    ///   - call: The CAPPluginCall object representing the plugin call.
    public func login(_ account: Account, _ call: CAPPluginCall){
        let dayCutoff: Int = 15
        let username: String = account.user
        guard let retailer: BRAccountLinkingRetailer =
                RetailerEnum(rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            call.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        guard let password: String = account.password else {
            call.reject("Password is required for login")
            return
        }
        let connection = BRAccountLinkingConnection( retailer: retailer, username: username,
                                                     password: password)
        connection.configuration.dayCutoff = dayCutoff
        connection.configuration.returnLatestOrdersOnly = false
        connection.configuration.countryCode = "US"
        let error = BRAccountLinkingManager.shared().linkRetailer(with: connection)
        if (error == .none) {
            // Success
            Task(priority: .high){
                await BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: {
                    error, viewController, sessionId in
                    self.verifyRetailerCallback(error,viewController, connection, call, account)
                })
                call.resolve(account.toResultData())
            }
        }else {
            call.reject("Login Error")
        }
    }
    /// Logs out a user account.
     ///
     /// - Parameters:
     ///   - call: The CAPPluginCall object representing the plugin call.
     ///   - account: An instance of the Account struct containing user and account information.
    public func logout(_ call: CAPPluginCall, _ account: Account) {
        if (account.user == "" && account.accountType.source == "") {
            BRAccountLinkingManager.shared().unlinkAllAccounts {
                call.resolve()
            }
            return
        }
        
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(
            rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            call.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        if (account.accountType.source != "") {
            BRAccountLinkingManager.shared().unlinkAccount(for: retailer) {
                call.resolve()
            }
        }
    }
    /// Retrieves orders for a specific user account or for all linked accounts.
    ///
    /// - Parameters:
    ///   - account: An optional instance of the Account struct containing user and account information.
    ///   - call: The CAPPluginCall object representing the plugin call.
    public func orders(_ account: Account?, _ call: CAPPluginCall){
        if(account == nil){
            allAccountsOrders(call)
            return
        }
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(
            rawValue: account!.accountType.source)!.toBRAccountLinkingRetailer() else {
            call.reject("Unsuported retailer \(account!.accountType.source)")
            return
        }
        
        BRAccountLinkingManager.shared().grabNewOrders(for: .walmart) { retailer, order, remaining, viewController, errorCode, sessionId in
            if(errorCode == .none && order != nil){
                call.resolve(RspReceipt(scanResults: order!).toPluginCallResultData())
            }
        }
    }
    
    /// Retrieves orders for all linked accounts.
    ///
    /// - Parameters:
    ///   - call: The CAPPluginCall object representing the plugin call.
    public func allAccountsOrders(_ call: CAPPluginCall){
        call.keepAlive = true
        var returnedAccounts = 0
        Task(priority: .high) {
            let retailers = await BRAccountLinkingManager.shared().getLinkedRetailers()
            for ret in  retailers {
                DispatchQueue.main.async {
                    BRAccountLinkingManager.shared().grabNewOrders( for: .amazonBeta) { retailer, order, remaining, viewController, errorCode, sessionId in
                        if(errorCode == .none && order != nil){
                            call.resolve(RspReceipt(scanResults: order!).toPluginCallResultData())
                            returnedAccounts+=1
                            if (returnedAccounts >= retailers.count){
                                call.keepAlive = false
                                call.resolve()
                            }
                        }
                    }
                }
            }
        }
    }
    
    /// Retrieves a list of linked accounts.
    ///
    /// - Returns: An array of Account objects representing linked accounts.
    public func accounts () -> [Account] {
        var accountsList = [Account]()
            let retailers = BRAccountLinkingManager.shared().getLinkedRetailers()
            for ret in retailers {
                let connection =  BRAccountLinkingManager.shared().getLinkedRetailerConnection(BRAccountLinkingRetailer(rawValue: ret.uintValue)!)
                let isVerified =  false
                let accountCommon = AccountCommon.init(type: .retailer, source: RetailerEnum.fromBRAccountLinkingRetailer(BRAccountLinkingRetailer(rawValue: ret.uintValue)!).toString()!)
                let account = Account(accountType: accountCommon, user: connection!.username!, password: connection!.password!, isVerified: isVerified)
                accountsList.append(account)
            }
        return accountsList

    }
    /// Handles the callback after attempting to verify a retailer account.
    ///
    /// - Parameters:
    ///   - error: The BRAccountLinkingError code indicating the result of the verification.
    ///   - viewController: The UIViewController to present for verification if needed.
    ///   - connection: The BRAccountLinkingConnection object representing the user's account connection.
    ///   - call: The CAPPluginCall object representing the plugin call.
    ///   - account: An instance of the Account struct containing user and account information.
    private func verifyRetailerCallback(
        _ error: BRAccountLinkingError,
        _ viewController: UIViewController?,
        _ connection: BRAccountLinkingConnection,
        _ call: CAPPluginCall,
        _ account: Account)
    {
        switch error {
        case .verificationNeeded :
            let rootVc = UIApplication.shared.windows.first?.rootViewController
            rootVc!.present(viewController!, animated: true, completion: nil)
            break
        case .verificationCompleted :
            let rootVc = UIApplication.shared.windows.first?.rootViewController
            rootVc!.dismiss(animated: true)
            break
        case .noCredentials :
            call.reject("Credentials have not been provided")
            break
        case .internal :
            call.reject("Unexpected error: internal")
            break
        case .parsingFail :
            call.reject("General Error: parsing fail")
            break
        case .invalidCredentials :
            call.reject("Invalid credentials. Please verify provided username and password.")
            break
        case .cancelled :
            viewController?.dismiss(animated: true)
            call.reject("Account login canceled.")
            break
        default :
            BRAccountLinkingManager.shared().linkRetailer(with: connection)
            account.isVerified = true
            call.resolve(RspAccount(account: account).toResultData())
        }
    }
}

