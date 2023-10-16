/*
 * ReceiptCapturePlugin Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/// A Swift class representing a Capacitor plugin for receipt capture and management.
import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

@objc(ReceiptCapturePlugin)
public class CaptureReceiptPlugin: CAPPlugin {
    
    private let receiptCapture = CaptureReceipt()
    
    /// Initializes the plugin with the provided license and product keys.
    ///
    /// - Parameter call: The CAPPluginCall representing the initialization request.
    @objc public func initialize(_ call: CAPPluginCall) {
        let reqInitialize = try! ReqInitialize(call)
        receiptCapture.initialize(licenseKey: reqInitialize.licenseKey, productKey: reqInitialize.productKey)
        call.resolve()
    }
    
    /// Handles user lgoin from receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the logout request.
    @objc public func login(_ call: CAPPluginCall) {
        let reqAccount = try! ReqAccount(call)
        receiptCapture.login(account: reqAccount.account(),
                             onError: { error, errorCode in
                                            let errorMsg = error
                                            let code = errorCode.rawValue.description
                                            let rspError = RspError(requestId: reqAccount.requestId, message: error, code: errorCode).toPluginCallResultData()
                                            call.reject(errorMsg, code, .none, rspError)
                            },
                             onComplete: { account in call.resolve( (account.toResultData()) )} )
    }

    /// Handles user logout from receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the logout request.
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

    
    /// Retrieves a list of  accounts for receipt management.
    ///
    /// - Parameter call: The CAPPluginCall representing the request for account information.
    @objc func accounts(_ call: CAPPluginCall){
        let req = try! Req(call)
        receiptCapture.accounts(
            onError: {error in self.onError(req.requestId, error)},
            onComplete: { [self] in onComplete(requestId: req.requestId)},
            onAccount: {account in onAccount(requestId: req.requestId, account: account)})
    }
    
    /// Initiates e-receipt scanning.
    ///
    /// - Parameter call: The CAPPluginCall representing the scan request.
    @objc func scan(_ call: CAPPluginCall) {
        let req = try! Req(call)
        receiptCapture.scan(onError: {error in self.onError(req.requestId, error)},
                            onReceipt: { receipt in self.onReceipt(req.requestId, Receipt(requestId: req.requestId, scanResults: receipt))},
                            onComplete: {})
    }
    
    // MARK: - Private Helper Methods
    
    /// Notifies the listeners with receipt scan results.
    ///
    /// - Parameters:
    ///   - requestId: The unique identifier for the request.
    ///   - scanResults: The results of the receipt scan.
    private func onReceipt(_ requestId: String, _ receipt: Receipt){
        let rsp = RspReceipt(requestId: requestId, receipt: receipt).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    /// Notifies the listeners about an error.
    ///
    /// - Parameters:
    ///   - requestId: The unique identifier for the request.
    ///   - message: The error message.
    private func onError(_ requestId: String, _ message: String){
        let rsp = RspError(requestId: requestId, message: message).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    /// Notifies the listeners about the completion of an operation.
    ///
    /// - Parameter requestId: The unique identifier for the request.
    private func onComplete(requestId: String){
        let rsp = Rsp(requestId: requestId, event: .onComplete).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
    
    /// Notifies the listeners with account information.
    ///
    /// - Parameters:
    ///   - requestId: The unique identifier for the request.
    ///   - account: The account information.
    private func onAccount(requestId: String, account: Account) {
        let rsp = RspAccount(requestId: requestId, account: account).toPluginCallResultData()
        self.notifyListeners("onCapturePluginResult", data: rsp)
    }
}
