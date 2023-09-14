/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

struct RspInitialized : Rsp {

    
    private let isInitialized: Bool

    init(isInitialized: Bool){
        self.isInitialized = isInitialized
    }
 
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "isInitialized" : isInitialized
        ]
    }
}
