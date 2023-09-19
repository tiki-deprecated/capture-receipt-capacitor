/*
 * ReqLogout Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A class representing the logout request parameters for the ReceiptCapture plugin.
public class ReqLogout {
    
    /// The username associated with the account to be logged out.
    var username: String
    
    /// The password associated with the account to be logged out.
    var password: String
    
    /// The source or account type for the logout operation.
    var source: String
    
    /// Initializes a `ReqLogout` instance based on the provided Capacitor plugin call.
    ///
    /// - Parameter data: The Capacitor plugin call containing logout parameters.
    init (data: CAPPluginCall){
        // Retrieve the username from the plugin call or set it to an empty string if not present.
        username = data.getString("username") ?? ""
        
        // Retrieve the password from the plugin call or set it to an empty string if not present.
        password = data.getString("password") ?? ""
        
        // Retrieve the source (account type) from the plugin call or set it to an empty string if not present.
        source = data.getString("account_type") ?? ""
    }
}
