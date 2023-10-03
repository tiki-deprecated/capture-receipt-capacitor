/*
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
    
    /// Initializes a `ReqInitialize` struct based on the provided Capacitor plugin call.
    ///
    /// - Parameter call: The Capacitor plugin call containing initialization parameters.
    init(licenseKey: String, productKey: String) {
        // Set the license key.
        self.licenseKey = licenseKey
        
        // Set the product key.
        self.productKey = productKey
        
    }
}
