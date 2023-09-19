/*
 * ReqLogin Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A class representing the login request parameters for the ReceiptCapture plugin.
public class ReqLogin {
    
    /// The username for authentication.
    var username: String
    
    /// The password for authentication.
    var password: String
    
    /// The source or account type for login.
    var source: String
    
    /// Initializes a `ReqLogin` instance based on the provided Capacitor plugin call.
    ///
    /// - Parameter data: The Capacitor plugin call containing login parameters.
    init (data: CAPPluginCall){
        // Retrieve the username from the plugin call or set it to an empty string if not present.
        username = data.getString("username") ?? ""
        
        // Retrieve the password from the plugin call or set it to an empty string if not present.
        password = data.getString("password") ?? ""
        
        // Retrieve the source (account type) from the plugin call or set it to an empty string if not present.
        source = data.getString("source") ?? ""
    }
}
