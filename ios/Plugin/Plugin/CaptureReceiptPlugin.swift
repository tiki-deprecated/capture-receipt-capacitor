/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

@objc(ReceiptCapturePlugin)
public class CaptureReceiptPlugin: CAPPlugin {
    
    private let receiptCapture = CaptureReceipt()
    
    @objc public func initialize(_ call: CAPPluginCall) {
        let reqInitialize = try! ReqInitialize(call)
        receiptCapture.initialize(licenseKey: reqInitialize.licenseKey, productKey: reqInitialize.productKey)
        call.resolve()
    }
    
    @objc public func login(_ call: CAPPluginCall) {
        let reqAccount = try! ReqAccount(call)
        receiptCapture.login(account: reqAccount.account(),
                             onError: { error in call.reject(error)},
                             onComplete: { account in call.resolve( (account.toResultData()) )} )
    }

    @objc func logout(_ call: CAPPluginCall) {
        let reqAccount = try? ReqAccount(call)
        if(reqAccount == nil){
            receiptCapture.logout(
                onError: { error in call.reject(error)},
                onComplete: { call.resolve() })
        }else{
            receiptCapture.logout(
                onError: { error in call.reject(error)},
                onComplete: { call.resolve() },
                account: reqAccount!.account())
        }
    }

    @objc func accounts(_ call: CAPPluginCall){
        let req = try! Req(call)
        receiptCapture.accounts(
            onError: {error in self.onError(req.requestId, error)},
            onComplete: { [self] in onComplete(requestId: req.requestId)},
            onAccount: {account in onAccount(requestId: req.requestId, account: account)})
    }
    
    @objc func scan(_ call: CAPPluginCall) {
        let req = try! Req(call)
        receiptCapture.scan(onError: {error in self.onError(req.requestId, error)},
                            onReceipt: { receipt in self.onReceipt(req.requestId, receipt)},
                            onComplete: {})
    }
    
    private func onReceipt(_ requestId: String, _ scanResults: BRScanResults){
        let rsp = RspReceipt(requestId: requestId, scanResults: scanResults).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    private func onError(_ requestId: String, _ message: String){
        let rsp = RspError(requestId: requestId, message: message).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    private func onComplete(requestId: String){
        let rsp = Rsp(requestId: requestId, event: .onComplete).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    private func onAccount(requestId: String, account: Account) {
        let rsp = RspAccount(requestId: requestId, account: account).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
}
