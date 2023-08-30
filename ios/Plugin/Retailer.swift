/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Retailer : CAPPlugin{
    
    let id: Int
    let bannerId: Int
    
    public func initialize(_ req: ReqInitialize) {
        BRScanManager.shared().licenseKey = req.licenseKey
        BRScanManager.shared().prodIntelKey = req.productKey
        BRAccountLinkingManager.shared()
    }
    
    
    public func login(_ account: Account, _ pluginCall: CAPPluginCall){
        let dayCutoff: Int = 15
        let username: String = account.user
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            pluginCall.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        guard let password: String = account.password else {
            pluginCall.reject("Password is required for login")
            return
        }
        let connection = BRAccountLinkingConnection(retailer: retailer, username: username, password: password)
        connection.configuration.dayCutoff = dayCutoff
        connection.configuration.returnLatestOrdersOnly = false
        connection.configuration.countryCode = "US"
    
        BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: { error, viewController, sessionId in
            self.verifyRetailerCallback(error,viewController, connection, pluginCall, account)
        })
    }

    private func verifyRetailerCallback(
        _ error: BRAccountLinkingError,
        _ viewController: UIViewController?,
        _ connection: BRAccountLinkingConnection,
        _ pluginCall: CAPPluginCall,
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
                pluginCall.reject("Credentials have not been provided")
                break
            case .internal :
                pluginCall.reject("Unexpected error: internal")
                break
            case .parsingFail :
                pluginCall.reject("General Error: parsing fail")
                break
            case .invalidCredentials :
                pluginCall.reject("Invalid credentials. Please verify provided username and password.")
                break
            case .cancelled :
                viewController?.dismiss(animated: true)
                pluginCall.reject("Account login canceled.")
                break
            default :
                BRAccountLinkingManager.shared().linkRetailer(with: connection)
                account.isVerified = true
                pluginCall.resolve(RspAccount(account: account).toResultData())
            }
    }
    
    
    public func logout(_ account: Account, _ pluginCall: CAPPluginCall) {
        
        if (account.user == "" && account.accountType.source == "") {
            BRAccountLinkingManager().unlinkAllAccounts {
                pluginCall.resolve()
            }
        }
        
        guard let retailer: BRAccountLinkingRetailer = RetailerEnum(rawValue: account.accountType.source)?.toBRAccountLinkingRetailer() else {
            pluginCall.reject("Unsuported retailer \(account.accountType.source)")
            return
        }
        if (account.accountType.source != "") {
            BRAccountLinkingManager().unlinkAccount(for: retailer) {
                pluginCall.resolve()
            }
        }
    }
    
    public func orders(retailer :BRAccountLinkingRetailer, completion: @escaping (_ success: Bool, _ retailer: BRAccountLinkingRetailer, _ remaining: Int, _ sessionId: String, _ erroCode: Error?) -> Void){

        let taskId = BRAccountLinkingManager.shared().grabNewOrders(for: retailer) { retailer, order, remaining, viewController, errorCode, sessionId in
          if (errorCode == .verificationNeeded) {
            // Display UIViewController for 2FA verification, CAPTCHA or other user input
              print("###GrabOrders verification needed")
              self.VerificationNeededPresent(viewController: viewController!)
          } else if errorCode == .invalidKey {
              print("####InvalidKey")
          }else if errorCode == .verificationCompleted {
            // Dismiss the previously presented UIViewController
              self.VerificationNeededDismiss()
          } else if errorCode == .none {
              print("###Everiting Works Fine")
            // Order may be returned here
            //  call.resolve(order)
              print(order?.description)
            if (remaining <= 0) {
              // Grab Orders Completed Successfully
              // No more orders to fetch
            }
          } else {
            // Grab Orders Failed. Check error for more info
          }
            
        }
    }
    
    public func accounts (_ pluginCall: CAPPluginCall) -> [Account] {
        var retailers = [BRAccountLinkingRetailer]()
        for ret in BRAccountLinkingManager().getLinkedRetailers() {
            retailers.append(BRAccountLinkingRetailer(rawValue: ret.uintValue)!)
        }
        
        var accountsList = [Account]()
        for retLinked in retailers {
            let connection = BRAccountLinkingManager().getLinkedRetailerConnection(retLinked)
            var isVerified =  false
            BRAccountLinkingManager.shared().verifyRetailer(with: connection!) { error, viewController, sessionId in
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
    
    
    public func VerificationNeededPresent(viewController: UIViewController){
        print("###verification needed")
        
    }
    public func VerificationNeededDismiss(){
        print("###verification needed Dismiss")

    }

}

