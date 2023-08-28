import Foundation
import Capacitor

@objc (ReceiptCapture)
public class ReceiptCapture: NSObject {
//    let email
//    let physical
    
    var retailer: Retailer? = nil
    
    public func initialize(_ call: CAPPluginCall) {
        retailer = Retailer(ReqInitialize(call))
    }
}
