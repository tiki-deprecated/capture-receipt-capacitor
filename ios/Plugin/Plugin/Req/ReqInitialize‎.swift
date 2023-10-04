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
        if(call.getString("productKey") == nil){
          call.reject("Please, provide a valid ProductKey")
          throw NSError(
              domain: "tiki",
              code: 400,
              userInfo:["message": "Provide a Product Intelligence Key for initialization."]
          )
        }
        if(call.getString("ios") == nil ){
          call.reject("Please, provide a valid iOS License key")
          throw NSError(
              domain: "tiki",
              code: 400,
              userInfo:["message": "Provide an iOS License Key for initialization."]
          )
        }
        licenseKey = call.getString("ios")!
        productKey = call.getString("productKey")!
        try super.init(call)
    }
}
