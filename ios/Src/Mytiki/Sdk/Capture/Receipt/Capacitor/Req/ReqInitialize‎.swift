/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

struct ReqInitialize: Encodable {
    var licenseKey: String
    var productKey: String
}
