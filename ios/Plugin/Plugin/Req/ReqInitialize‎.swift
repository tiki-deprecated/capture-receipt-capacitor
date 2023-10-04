/*
 * ReqInitialize Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor


/// A class representing the initialization request parameters for the ReceiptCapture plugin.
public class ReqInitialize : Req{
    
    /// The license key used for authentication.
    var licenseKey: String
    
    /// The product key associated with the ReceiptCapture plugin.
    var productKey: String
    
    
    /// Initializes the ReqInitialize class with data from a CAPPluginCall.
    ///
    /// - Parameter call: The CAPPluginCall object representing the request.
    /// - Throws: An error if required data is missing.
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
