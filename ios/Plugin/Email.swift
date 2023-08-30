//
//  Email.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 29/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor


public class email {
    public init (){
        
    }
    
    public func account(_ account: Account, _ pluginCall: CAPPluginCall) {

        let provider = EmailCommon(rawValue:  account.accountType.source)?.toBREReceiptProvider()
        let rootVc = UIApplication.shared.windows.first?.rootViewController
        var email = BRIMAPAccount.init(provider: provider!, email: account.user, password: account.password!)
        BREReceiptManager().setupIMAP(for: email, viewController: rootVc!) { result in
            if result == .createdAppPassword {
                print("Successfully created app password.")
            } else if result == .enabledLSA {
                print("Successfully enabled Gmail Less Secure Apps.")
            } else if result == .saved {
                print("saved")
            } else if result == .userCancelled {
                print("user canceled")
            }else if result == .adminNeeded {
                print("admin needed")
            }else if result == .unknownFailure {
                print("UNKNOW FAILURE")
            }
        }
        
        BREReceiptManager().linkIMAPAccountWithoutSetup(email) { result in
            if result == .createdAppPassword {
                print("Successfully created app password.")
            } else if result == .enabledLSA {
                print("Successfully enabled Gmail Less Secure Apps.")
            } else if result == .saved {
                print("saved")
            } else if result == .userCancelled {
                print("user canceled")
            }else if result == .adminNeeded {
                print("admin needed")
            }else if result == .unknownFailure {
                print("UNKNOW FAILURE")
            }
        }
        
        BREReceiptManager.shared().verifyImapAccount(email) { success, error in
            if success {
                //Credentials verified
                print("Credentials verified.")
            } else {
                //Failed to verify credentials
                print(error)

            }
        }
    }
}
