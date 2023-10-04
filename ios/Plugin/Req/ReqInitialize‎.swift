/*
 * ReqInitialize Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

/// A structure representing the initialization request parameters for the ReceiptCapture plugin.
public struct ReqInitialize {
    
    /// The iOS license key for the package
    var ios: String
    
    /// The product intelligence key for the package.
    var product: String
    
    /// The Google client ID used for authentication (optional).
    var googleClientId : String?
    
    /// Initializes a `ReqInitialize` struct based on the provided Capacitor plugin call.
    ///
    /// - Parameter call: The Capacitor plugin call containing initialization parameters.
    init(
        _ call: CAPPluginCall
    ) throws {
        ios = try call.getString(
            "ios"
        ) ?? {
            throw NSError(
                domain: "tiki",
                code: 400,
                userInfo:["message": "Provide an iOS License Key for initialization."]
            )
        }()
        
        product = try call.getString("product") ?? {
            throw NSError(
                domain: "tiki",
                code: 400,
                userInfo:["message": "Provide a Product Intelligence Key for initialization."]
            )
        }()
        
        // Retrieve the Google client ID from the plugin call, if provided.
        googleClientId = call.getString(
            "googleClientId"
        )
    }
}
