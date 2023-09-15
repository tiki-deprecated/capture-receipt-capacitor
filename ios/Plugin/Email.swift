/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Email {
    
    let receiptManager = BREReceiptManager()
    
    public init(_ licenseKey: String, _ productKey: String, _ googleClientId: String?)  {
        DispatchQueue.main.async {
            BRScanManager.shared().licenseKey = licenseKey
            BRScanManager.shared().prodIntelKey = productKey
            BREReceiptManager.shared().googleClientId = googleClientId
            BRAccountLinkingManager.shared()
        }
    }
    
    public init(){
    }
    
    public func login(_ account: Account, _ pluginCall: CAPPluginCall) {
        let provider = EmailEnum(rawValue:  account.accountType.source)?.toBREReceiptProvider()
        if (provider == .gmail) {
            loginOauth(pluginCall)
            return
        }else{
            var email = BRIMAPAccount(provider: provider!, email: account.user, password: account.password!)
            let rootVc = UIApplication.shared.windows.first?.rootViewController

            BREReceiptManager.shared().setupIMAP(for: email, viewController: rootVc!) { result in
                if result == .createdAppPassword {
                    print("Successfully created app password.")
                } else {
                    
                }
            }
        }
        
    }
    
    public func logout(_ pluginCall: CAPPluginCall, _ account: Account?){
        if(account != nil){
            let provider = EmailEnum(rawValue:  account!.accountType.source)?.toBREReceiptProvider()
            var email = BRIMAPAccount(provider: provider!, email: account!.user, password: account!.password!)
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
    
    public func scan(_ pluginCall: CAPPluginCall, _ account: Account?){
        if(account != nil){
            let provider = EmailEnum(rawValue:  account!.accountType.source)?.toBREReceiptProvider()
            var email = BRIMAPAccount(provider: provider!, email: account!.user, password: account!.password!)
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
            }
        }else{
            scanOauth(pluginCall)
            BRIMAPAccount(provider: .gmail, email: "", password: "")
            var emailgmail =
            BREReceiptManager.shared().getEReceipts(for:  BRIMAPAccount(provider: .gmail, email: "", password: ""), withCompletion: {scanResults, emailAccount, error in
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
            })
        }
    }
    
    public func accounts() -> [Account]{
        var verifiedAccounts: [Account] = []
        let linkedAccounts = BREReceiptManager.shared().getLinkedAccounts()
        linkedAccounts?.forEach{ brAccount in
            var account = Account(provider: brAccount.provider, email:brAccount.email)
            account.isVerified = true
            verifiedAccounts.append(account)
        }
        return verifiedAccounts
    }
    
    func loginOauth(_ pluginCall: CAPPluginCall){
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        DispatchQueue.main.async {
            BREReceiptManager.shared().beginOAuth(for: .gmail, with: rootVc!, andCompletion: { error in
                if error == nil {
                    // Account successfully authenticated
                    pluginCall.resolve()
                }else{
                    // Account problem
                    pluginCall.reject(error.debugDescription)
                }
            })
        }
    }
    
    func scanOauth(_ pluginCall: CAPPluginCall){
//        let linkedAccounts = BREReceiptManager.shared().getLinkedAccounts()
//        linkedAccounts?.forEach{ brAccount in
//            if(brAccount.provider == .gmail){
//
//            }
//        }
//        

        BREReceiptManager.shared().getEReceipts(){scanResults, emailAccount, error in
            if(scanResults != nil){
                scanResults?.forEach{scanResults in
                    print(
                        RspScan(scan: RspReceipt(scanResults: scanResults),
                                account: Account(provider: emailAccount!.provider, email: emailAccount!.email))
                    )
                }
            }else{
                print(error?.localizedDescription ?? "No receipts.")
            }
        }

    }
}
