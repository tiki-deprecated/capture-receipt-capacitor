import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

@objc(ReceiptCapturePlugin)
public class ReceiptCapturePlugin: CAPPlugin {
    private let receiptCapture = ReceiptCapture()
    
    public func initialize (_ call: CAPPluginCall) {
        receiptCapture.initialize(call)
    }

    public func login(call: CAPPluginCall) {
        let ret = call.getString("retailer")!
        let retailer = RetailerCommon(rawValue: ret)?.toBRAccountLinkingRetailer()
        let username = call.getString("username")
        let password = call.getString("password")
        
        Retailer().login(retailer: retailer!, username: username!, password: password!, dayCutoff: 500)
    }
    
    func logout (call: CAPPluginCall) {
        let username = call.getString("username")
        let password = call.getString("source")

        if (username && password != nil){
            let logoutError = Retailer().manager
        }
    }
    
    func accounts(call: CAPPluginCall){
        
    }
    
    func scan(call: CAPPluginCall) {
        
    }
}
