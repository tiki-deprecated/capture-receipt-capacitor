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
    
    @objc public func login(_ call: CAPPluginCall) {
        receiptCapture.login(call)
    }

    @objc func logout(_ call: CAPPluginCall) {
        receiptCapture.logout(call)
    }

    @objc func accounts(_ call: CAPPluginCall){
        receiptCapture.accounts(call)
    }
    
    @objc func scan(_ call: CAPPluginCall) {
        receiptCapture.scan(call)
    }
}
