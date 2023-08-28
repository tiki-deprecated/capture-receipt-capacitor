/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspInitialized : Rsp {
    private let isInitialized: Bool

    init(isInitialized: Bool){
        isInitialized = isInitialized
    }
    func toJson() -> JSObject {
        JSObject.updateValue("isInitialized", isInitialized)
    }
}
