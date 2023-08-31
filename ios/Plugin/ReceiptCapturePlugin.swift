/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

@objc(ReceiptCapturePlugin)
public class ReceiptCapturePlugin: CAPPlugin {
    private let receiptCapture = ReceiptCapture()
    
    @objc public func initialize(_ call: CAPPluginCall) {
        receiptCapture.initialize(call)
    }
    
    @objc public func login(call: CAPPluginCall) {
        let reqLogin = ReqLogin(data: call)
        guard let accountType = AccountCommon.defaults[reqLogin.source] else {
            call.reject("Invalid source: \(reqLogin.source)")
            return
        }
        let account = Account.init(accountType: accountType, user: reqLogin.username, password: reqLogin.password, isVerified: false)
        switch account.accountType.type {
            case .email :
                call.reject("Email login not implemented")
                break
            case .retailer :
                guard let retailer = receiptCapture.retailer else {
                    call.reject("Call plugin initialize method.")
                    return
                }
                retailer.login(account, call)
                break
        }
    }

    @objc func logout(call: CAPPluginCall) {
        let reqLogout = ReqLogin(data: call)
        guard let accountType = AccountCommon.defaults[reqLogout.source] else {
            call.reject("Invalid source: \(reqLogout.source)")
            return
        }
        
        let account = Account.init(accountType: accountType, user: reqLogout.username, password: reqLogout.password, isVerified: false)
        switch account.accountType.type {
            case .email :
                call.reject("Email login not implemented")
                break
            case .retailer :
                guard let retailer = receiptCapture.retailer else {
                    call.reject("Call plugin initialize method.")
                    return
                }
                retailer.logout(account, call)
                break
            }
        }

    @objc func accounts(call: CAPPluginCall){
        
        guard let retailer = receiptCapture.retailer else {
            call.reject("Call plugin initialize method.")
            return
        }
        
        let data = retailer.accounts(call)
//      TODO Implement E-Mail case
//        call.resolve(data)
        
    }
    
    func scan(call: CAPPluginCall) {
        
    
    }
}
