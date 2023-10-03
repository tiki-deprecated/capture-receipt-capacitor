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
        if(call.getString("productKey") == "" || call.getString("licenseKey") == ""){
            call.reject("Please, provide a valid LicenseKey and ProductKey")
        }else{
            let reqInitialize = ReqInitialize(licenseKey: call.getString("productKey")!, productKey: call.getString("licenseKey")!)
            receiptCapture.initialize(reqInitialize: reqInitialize)
            call.resolve()
        }
    }
    
    @objc public func login(_ call: CAPPluginCall) {
        do{
            let reqAccount = try ReqAccount(data: call)
            receiptCapture.login(reqAccount: reqAccount, onError: { error in call.reject(error)} ,onComplete: { account in call.resolve(RspAccount(requestId: call.getString("requestId", ""), event: .onComplete, account: account!).toPluginCallResultData() )} )
        }catch {
            call.reject(error.localizedDescription)
        }
    }

    @objc func logout(_ call: CAPPluginCall) {
        do{
            receiptCapture.logout(reqAccount: try ReqAccount(data: call), onError: { error in call.reject(error)} ,onComplete: { call.resolve() })
        }catch{
            call.reject(error.localizedDescription)
        }
    }

    @objc func accounts(_ call: CAPPluginCall){
        receiptCapture.accounts(
            onError: {error in call.reject(error)},
            onComplete: { [self] in onComplete(requestId: call.getString("requestId", ""))},
            onAccount: {account in onAccounts(requestId: call.getString("requestId", ""), account: account)})
    }
    
    @objc func scan(_ call: CAPPluginCall) {
        let rsp = Rsp(requestId: call.getString("requestId", ""), event: .onReceipt)
        receiptCapture.scan(call: call,
                            reqScan: ReqScan(data: call),
                            onError: {error in call.reject(error)},
                            onReceipt: { rspReceipt in self.onScan(call.getString("requestId", ""), rspReceipt)},
                            onComplete: {})
    }
    
    func onScan(_ requestId: String, _ rspReceipt: RspReceipt? = nil){
        self.notifyListeners("onReceipt", data: rspReceipt?.toPluginCallResultData())
    }
    
    private func onComplete(requestId: String){
        let rsp = Rsp(requestId: requestId, event: .onComplete)
        self.notifyListeners("onComplete", data: rsp.toPluginCallResultData())
    }
    private func onAccounts(requestId: String, account: Account) {
        let rsp = RspAccount(requestId: requestId, event: .onAccount, account: account)
        self.notifyListeners("onAccount", data: account.toResultData())
    }
}
