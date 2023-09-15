/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public struct ReqInitialize {
    
    var licenseKey: String
    var productKey: String
    var googleClientId : String?
    
    init(_ call: CAPPluginCall) {
        licenseKey = call.getString("licenseKey") ?? ""
        productKey = call.getString("productKey") ?? ""
        googleClientId = call.getString("googleClientId") ?? nil
    }

}

