/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A structure representing the initialization request parameters for the ReceiptCapture plugin.
public class ReqInitialize : Req{
    
    /// The license key used for authentication.
    var licenseKey: String
    
    /// The product key associated with the ReceiptCapture plugin.
    var productKey: String
    
    override init(_ call: CAPPluginCall) throws {
        if(call.getString("productKey") == nil || call.getString("licenseKey") == nil ){
            call.reject("Please, provide a valid LicenseKey and ProductKey")
            throw NSError()
        }
        licenseKey = call.getString("licenseKey")!
        productKey = call.getString("productKey")!
        try super.init(call)
    }
}
