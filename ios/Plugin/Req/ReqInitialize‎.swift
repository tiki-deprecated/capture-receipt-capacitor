/*
 * ReqInitialize Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A structure representing the initialization request parameters for the ReceiptCapture plugin.
public struct ReqInitialize {
    
    /// The license key used for authentication.
    var licenseKey: String
    
    /// The product key associated with the ReceiptCapture plugin.
    var productKey: String
    
    /// The Google client ID used for authentication (optional).
    var googleClientId : String?
    
    /// Initializes a `ReqInitialize` struct based on the provided Capacitor plugin call.
    ///
    /// - Parameter call: The Capacitor plugin call containing initialization parameters.
    init(_ call: CAPPluginCall) {
        // Retrieve the license key from the plugin call or set it to an empty string if not present.
        licenseKey = call.getString("licenseKey") ?? ""
        
        // Retrieve the product key from the plugin call or set it to an empty string if not present.
        productKey = call.getString("productKey") ?? ""
        
        // Retrieve the Google client ID from the plugin call, if provided.
        googleClientId = call.getString("googleClientId")
    }
}
