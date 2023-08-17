/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Retailer {
    
    public init(req: ReqInitialize) {
        BRScanManager.shared().licenseKey = req.licenseKey
        BRScanManager.shared().prodIntelKey = req.productKey
        BRAccountLinkingManager.shared()
    }
    public func account (retailer: RetailerCommon, username: String, password: String, dayCutoff: Int) {
        let connection = BRAccountLinkingConnection(retailer: retailer.toBRAccountLinkingRetailer()!, username: username, password: password)
        connection.configuration.dayCutoff = dayCutoff
        connection.configuration.returnLatestOrdersOnly = false
//        connection.configuration.countryCode = "US"
        let error = BRAccountLinkingManager.shared().linkRetailer(with: connection)
        if (error == .none) {
            print("xyz passou do primeiro if sem erros")
            verifyConnection(retailer: retailer, username: username, password: password)
        }

    }
    
    public func verifyConnection(retailer: RetailerCommon, username: String, password: String) {
        
        let connection = BRAccountLinkingConnection(retailer: retailer.toBRAccountLinkingRetailer()!, username: username, password: password)
        connection.configuration.dayCutoff = 30
        let taskId = BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: { errorCode, viewController, sessionId in

          if (errorCode == .verificationNeeded) {
              print("xyz Caiu no verification needed")
            // Display UIViewController for 2FA verification, CAPTCHA or other user input
//              viewController.shared.window.first?.rootViewController

          } else if (errorCode == .verificationCompleted) {
            // Dismiss the previously presented UIViewController
//              viewController!.dismiss(animated: true)

          } else {

            if (errorCode == .noCredentials) {
              // Error: Credentials have not been provided
            } else if (errorCode == .internal) {
              // Error: Unexpected error
            } else if (errorCode == .parsingFail) {
              // Error: General Error
            } else if (errorCode == .invalidCredentials) {
              // Error: Probable cause of the failure is invalid credentials
            } else if (errorCode == .cancelled) {
              // Operation has been cancelled.
              // Dismiss any previously presented user input UIViewController
            }
            // Verification Completed Successfully
            // Account can be linked
            BRAccountLinkingManager.shared().linkRetailer(with: connection)
              
          }
        })
    }
    
    public func orders(){
        let taskId = BRAccountLinkingManager.shared().grabNewOrders(for: .amazon) { retailer, order, remaining, viewController, errorCode, sessionID in

          if (errorCode == .verificationNeeded) {
            // Display UIViewController for 2FA verification, CAPTCHA or other user input
          } else if errorCode == .invalidKey {
              print("ALGO ERRADO NÃO ESTÁ CERTO")
          }else if errorCode == .verificationCompleted {
            // Dismiss the previously presented UIViewController
          } else if errorCode == .none {
            // Order may be returned here
            if (remaining <= 0) {
              // Grab Orders Completed Successfully
              // No more orders to fetch
            }
          } else {
            // Grab Orders Failed. Check error for more info
          }
        }
    }
    
    
}
//            how use the RetailerCommon
//            RetailerCommon(rawValue: "amazon").toBRAccountLinkingRetailer()
