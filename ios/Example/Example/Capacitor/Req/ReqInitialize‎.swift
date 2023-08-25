/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

public struct ReqInitialize: Encodable {
    var licenseKey: String
    var productKey: String
}
