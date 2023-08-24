import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

@objc(ReceiptCapturePlugin)
public class ReceiptCapturePlugin: CAPPlugin {
    private let receiptCapture = ReceiptCapture()
    
    @objc public func initialize (_ call: CAPPluginCall) {
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
    
    @objc func logout (call: CAPPluginCall) {

    }
    
    @objc func accounts(call: CAPPluginCall){
        
    }
    
    func scan(call: CAPPluginCall) {
        
    }
}
