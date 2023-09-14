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
    
    public func login(_ account: Account, _ pluginCall: CAPPluginCall) {
        let provider = EmailEnum(rawValue:  account.accountType.source)?.toBREReceiptProvider()
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        var email = BRIMAPAccount(provider: provider!, email: account.user, password: account.password!)
        receiptManager.setupIMAP(for: email, viewController: rootVc!) { result in
            if result == .createdAppPassword {
                print("Successfully created app password.")
            } else {
                
            }
        }
    }
    
    public func logout(_ pluginCall: CAPPluginCall, _ account: Account?){
        if(account != nil){
            let provider = EmailEnum(rawValue:  account!.accountType.source)?.toBREReceiptProvider()
            var email = BRIMAPAccount(provider: provider!, email: account!.user, password: account!.password!)
            receiptManager.signOut(from: email) { error in
                if(error != nil){
                    pluginCall.reject(error?.localizedDescription ?? "Email logout error.")
                }else{
                    pluginCall.resolve()
                }
            }
        }else{
            receiptManager.signOut{ error in
                if(error != nil){
                    pluginCall.reject(error?.localizedDescription ?? "Email logout error.")
                }else{
                    pluginCall.resolve()
                }
            }
        }
    }
    
    public func scan(_ pluginCall: CAPPluginCall, _ account: Account?){
        if(account != nil){
            let provider = EmailEnum(rawValue:  account!.accountType.source)?.toBREReceiptProvider()
            var email = BRIMAPAccount(provider: provider!, email: account!.user, password: account!.password!)
            receiptManager.getEReceipts(for: email){scanResults, emailAccount, error in
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
            receiptManager.getEReceipts{scanResults, emailAccount, error in
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
        }
    }
    
    public func accounts() -> [Account]{
        var verifiedAccounts: [Account] = []
        let linkedAccounts = receiptManager.getLinkedAccounts()
        linkedAccounts?.forEach{ brAccount in
            var account = Account(provider: brAccount.provider, email:brAccount.email)
            account.isVerified = true
            verifiedAccounts.append(account)
        }
        return verifiedAccounts
    }
    
}
