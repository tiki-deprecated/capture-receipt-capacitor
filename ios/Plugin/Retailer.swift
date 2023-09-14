/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Retailer : CAPPlugin{
    
    public init(_ licenseKey: String, _ productKey: String)  {
        DispatchQueue.main.async {
            BRScanManager.shared().licenseKey = licenseKey
            BRScanManager.shared().prodIntelKey = productKey
            BRAccountLinkingManager.shared()
        }
        super.init()
    }
    public override init() {
        super.init()
    }
    public func login(_ account: Account, _ call: CAPPluginCall){
        let dayCutoff: Int = 365
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
        
        BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: {
            error, viewController, sessionId in
            self.verifyRetailerCallback(error,viewController, connection, call, account)
        })
    }
    
    public func logout(_ account: Account, _ call: CAPPluginCall) {
        if (account.user == "" && account.accountType.source == "") {
            BRAccountLinkingManager.shared().unlinkAllAccounts {
                call.resolve()
            }
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
        
        let taskId = BRAccountLinkingManager.shared().grabNewOrders(for: retailer) { retailer, order, remaining, viewController, errorCode, sessionId in
            if(errorCode == .none && order != nil){
                call.resolve(RspReceipt(scanResults: order!).toPluginCallResultData())
            }
        }
    }
    public func allAccountsOrders(_ call: CAPPluginCall){
        call.keepAlive = true
        var returnedAccounts = 0
        let retailers = BRAccountLinkingManager.shared().getLinkedRetailers()
        for ret in  retailers {
            DispatchQueue.main.async {
                BRAccountLinkingManager.shared().grabNewOrders( for: BRAccountLinkingRetailer(rawValue: ret.uintValue)!) { retailer, order, remaining, viewController, errorCode, sessionId in
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
    
    public func accounts () -> [Account] {
        var retailers = BRAccountLinkingManager.shared().getLinkedRetailers()
        var accountsList = [Account]()
        for ret in retailers {
            let connection = BRAccountLinkingManager.shared().getLinkedRetailerConnection(BRAccountLinkingRetailer(rawValue: ret.uintValue)!)
            let isVerified =  false
            let source = RetailerEnum.AMAZON_BETA
            let accountCommon = AccountCommon.init(type: .retailer, source: "AMAZON")
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
            BRAccountLinkingManager.shared().linkRetailer(with: connection)
            account.isVerified = true
            call.resolve(RspAccount(account: account).toResultData())
        }
    }
}

