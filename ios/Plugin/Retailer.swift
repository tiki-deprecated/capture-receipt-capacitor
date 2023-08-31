/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Retailer : CAPPlugin{
    
    let linkingManager: BRAccountLinkingManager
    
    public init(_ licenseKey: String, _ productKey: String) {
        linkingManager = BRAccountLinkingManager.shared()
    }
    
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
    
        linkingManager.verifyRetailer(with: connection, withCompletion: {
            error, viewController, sessionId in
            self.verifyRetailerCallback(error,viewController, connection, call, account)
        })
    }

    public func logout(_ account: Account, _ call: CAPPluginCall) {
        if (account.user == "" && account.accountType.source == "") {
            linkingManager.unlinkAllAccounts {
                call.resolve()
            }
        }
        
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(
            rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            call.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        if (account.accountType.source != "") {
            linkingManager.unlinkAccount(for: retailer) {
                call.resolve()
            }
        }
    }
    
    public func orders(_ account: Account, _ call: CAPPluginCall){
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(
            rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            call.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        
        let taskId = linkingManager.grabNewOrders(for: retailer) { retailer, order, remaining, viewController, errorCode, sessionId in
            if(errorCode == .none && order != nil){
                let order = RspReceipt(scanResults: order!).toPluginCallResultData()
                call.resolve(order)
            }
        }
    }
    
    public func accounts (_ call: CAPPluginCall) -> [Account] {
        var retailers = [BRAccountLinkingRetailer]()
        for ret in linkingManager.getLinkedRetailers() {
            retailers.append(BRAccountLinkingRetailer(rawValue: ret.uintValue)!)
        }
        
        var accountsList = [Account]()
        for retLinked in retailers {
            let connection = linkingManager.getLinkedRetailerConnection(retLinked)
            var isVerified =  false
            linkingManager.verifyRetailer(with: connection!) { error, viewController, sessionId in
                if (error == .none || error == .accountLinkedAlready) {
                    isVerified = true
                }
            }
            
            let source = (RetailerEnum(rawValue: "")?.toString(retLinked.self)!)!
            let accountCommon = AccountCommon(type: .retailer, source: source)
            let account = Account(accountType: accountCommon, user: connection!.username!, password: connection!.password!, isVerified: isVerified)
            
            accountsList.append(account)
        }
                
        return accountsList
    }
    
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
                linkingManager.linkRetailer(with: connection)
                account.isVerified = true
                call.resolve(RspAccount(account: account).toResultData())
            }
    }
}

