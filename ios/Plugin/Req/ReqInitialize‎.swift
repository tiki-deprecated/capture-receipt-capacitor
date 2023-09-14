/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public struct ReqInitialize {
    
    var licenseKey: String
    var productKey: String
    
    init(_ call: CAPPluginCall) {
        licenseKey = call.getString("licenseKey") ?? ""
        productKey = call.getString("productKey") ?? ""
    }
}

