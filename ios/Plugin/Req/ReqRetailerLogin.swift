/*
 * ReqRetailerLogin Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A class representing the request parameters for retailer login in the ReceiptCapture plugin.
public class ReqRetailerLogin {
    
    /// The username associated with the retailer account to be logged in.
    var username: String
    
    /// The password associated with the retailer account to be logged in.
    var password: String
    
    /// The name or identifier of the retailer.
    var retailer: String
    
    /// Initializes a `ReqRetailerLogin` instance based on the provided Capacitor plugin call.
    ///
    /// - Parameter data: The Capacitor plugin call containing retailer login parameters.
    init (data: CAPPluginCall){
        // Retrieve the username from the plugin call or set it to an empty string if not present.
        username = data.getString("username") ?? ""
        
        // Retrieve the password from the plugin call or set it to an empty string if not present.
        password = data.getString("password") ?? ""
        
        // Retrieve the retailer name/identifier from the plugin call or set it to an empty string if not present.
        retailer = data.getString("retailer") ?? ""
    }
}
