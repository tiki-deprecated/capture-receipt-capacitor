/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public class ReqInitialize {
    var licenseKey: String
    var productKey: String
    init(data: JSObject) {
        licenseKey = data.getString("licenseKey") ?? ""
        productKey = data.getString("productKey") ?? ""
    }
}

