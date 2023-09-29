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
        if(call.getString("productKey") == "" || call.getString("licenseKey") == ""){
            call.reject("Please, provide an valid LicenseKey and ProductKey")
        }else{
            let reqInitialize = ReqInitialize(licenseKey: call.getString("productKey")!, productKey: call.getString("licenseKey")!)
            receiptCapture.initialize(reqInitialize: reqInitialize)
            call.resolve()
        }
    }
    
    @objc public func login(_ call: CAPPluginCall) {
        let reqLogin = ReqLogin(data: call)
        receiptCapture.login(reqLogin: reqLogin, onError: { error in call.reject(error)} ,onComplete: { account in call.resolve(RspAccount(account: account!).toResultData()) })
    }

    @objc func logout(_ call: CAPPluginCall) {
        receiptCapture.logout(reqAccount: ReqAccount(data: call), onError: { error in call.reject(error)} ,onComplete: { call.resolve() })
    }

    @objc func accounts(_ call: CAPPluginCall){
        receiptCapture.accounts(call)
    }
    
    @objc func scan(_ call: CAPPluginCall) {
        receiptCapture.scan(call)
    }
}
