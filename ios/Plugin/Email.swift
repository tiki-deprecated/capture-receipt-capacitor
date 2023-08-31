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
        let provider = EmailCommon(rawValue:  account.accountType.source)?.toBREReceiptProvider()
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        var email = BRIMAPAccount.init(provider: provider!, email: account.user, password: account.password!)
        receiptManager.setupIMAP(for: email, viewController: rootVc!) { result in
            if result == .createdAppPassword {
                print("Successfully created app password.")
            } else {
                
            }
        }
        
}
