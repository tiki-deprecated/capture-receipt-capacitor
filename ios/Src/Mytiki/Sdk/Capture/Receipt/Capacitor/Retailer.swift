/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

class Retailer {
    
    func initialize(req: ReqInitialize){
        BRScanManager.shared().licenseKey = req.licenseKey
        BRScanManager.shared().prodIntelKey = req.productKey
        BRAccountLinkingManager.shared()
    }
    
    func account (retailer: BRAccountLinkingRetailer, username: String, password: String, dayCutoff: Int) {
        let connection = BRAccountLinkingConnection(retailer: retailer, username: username, password: password)
        connection.configuration.dayCutoff = dayCutoff
        connection.configuration.returnLatestOrdersOnly = false
        connection.configuration.countryCode = "US"
        
        let error = BRAccountLinkingManager.shared().linkRetailer(with: connection)
        if (error == .none) {
          // Success
        }
        
        let taskId = BRAccountLinkingManager.shared().verifyRetailer(with: connection, withCompletion: { error, viewController, sessionId in

          if (error == .verificationNeeded) {
            // Display UIViewController for 2FA verification, CAPTCHA or other user input
//              viewController.shared.window.first?.rootViewController

          } else if (error == .verificationCompleted) {
            // Dismiss the previously presented UIViewController
//              viewController!.dismiss(animated: true)

          } else {

            if (error == .noCredentials) {
              // Error: Credentials have not been provided
            } else if (error == .internal) {
              // Error: Unexpected error
            } else if (error == .parsingFail) {
              // Error: General Error
            } else if (error == .invalidCredentials) {
              // Error: Probable cause of the failure is invalid credentials
            } else if (error == .cancelled) {
              // Operation has been cancelled.
              // Dismiss any previously presented user input UIViewController
            }
            // Verification Completed Successfully
            // Account can be linked
            BRAccountLinkingManager.shared().linkRetailer(with: connection)
          }
        })


    }
    
}
//            how use the RetailerCommon
//            RetailerCommon(rawValue: "amazon").toBRAccountLinkingRetailer()
