//
//  Test.swift
//  Test Microblink
//
//  Created by Jesse Monteiro Ferreira on 26/09/23.
//

import Foundation
import BlinkReceipt
import BlinkEReceipt

public class TestMicroblink {

    public init() {
        
    }
    public func signInAmazon(username: String, password: String){
        initialize()
        let connection = BRAccountLinkingConnection( retailer: .amazonBeta, username: username,
                                                     password: password)
        connection.configuration.dayCutoff = 7
        connection.configuration.returnLatestOrdersOnly = false
        connection.configuration.countryCode = "US"
        BRAccountLinkingManager.shared().unlinkAccount(for: .amazonBeta, withCompletion: {
            let error = BRAccountLinkingManager.shared().linkRetailer(with: connection)
            if (error == .none) {
                // Success
                BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: {
                    error, viewController, sessionId in
                    self.verifyRetailerCallback(error,viewController, connection)
                    print("Success Login")
                    BRAccountLinkingManager.shared().grabNewOrders(for: .amazonBeta) { retailer, order, remaining, viewController, errorCode, sessionId in
                        print(order)
                        print(errorCode.rawValue)
                    }
                })

            }else {
                print("Error Login")
            }
        })
        

    }
    

    public func signInGmail(username: String, password: String, appendLog: @escaping (String) -> Void)  {
        initialize()
        BREReceiptManager.shared().dayCutoff = 7
        BREReceiptManager.shared().signOut(completion: { error in
            let email = BRIMAPAccount(provider: .gmailIMAP, email: username, password: password)
            let rootVc = UIApplication.shared.windows.first?.rootViewController
            BREReceiptManager.shared().setupIMAP(for: email, viewController: rootVc!, withCompletion: { result in
                BREReceiptManager.shared().verifyImapAccount(email, withCompletion: { success, error in
                    if success {
                        print("Time Start Scan")
                        appendLog("Time Start Scan")
                        print(Date.now.description)
                        appendLog(Date.now.description)
                        BREReceiptManager.shared().getEReceipts(){scanResults, emailAccount, error in
                            if(scanResults != nil){
                                for scanResult in scanResults! {
                                    appendLog(scanResult.merchantName.value ?? "")
                                    appendLog(scanResult.total.description ?? "")
                                    appendLog(scanResult.receiptDate.value ?? "")
                                }
                                print("Time End Scan")
                                appendLog("Time End Scan")
                                print(Date.now.description)
                                appendLog(Date.now.description)
                            }else{
                                print("Error print")
                            }
                        }
                    } else {
                        print("Error")
                    }
                })
            })
        
        })




        
    }

    public func initialize(){
        BRScanManager.shared().licenseKey = "sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/j3oG8q4R37b51+hnxE+gy+INAyCYTh8o8wWbXhW0ljpl1WVyCHnJmip2gdcZKWJRSh4U5cSQLUTQg8ZpEngcde9h/8etQFq7M69BYu64NY7NL82VAFuVn+ie2ViSlnw+rPBAMqb5aq/v58fV3JOmWj+b6Y//9xHiAhd"
        BRScanManager.shared().prodIntelKey = "wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw=="
        BRAccountLinkingManager.shared()

    }
    public func verifyRetailerCallback(
        _ error: BRAccountLinkingError,
        _ viewController: UIViewController?,
        _ connection: BRAccountLinkingConnection)
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
            print("Credentials have not been provided")
            break
        case .internal :
            print("Unexpected error: internal")
            break
        case .parsingFail :
            print("General Error: parsing fail")
            break
        case .invalidCredentials :
            print("Invalid credentials. Please verify provided username and password.")
            break
        case .cancelled :
            viewController?.dismiss(animated: true)
            print("Account login canceled.")
            break
        default :
            break
        }
    }
}
