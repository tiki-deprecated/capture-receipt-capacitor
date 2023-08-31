/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public struct RspScan : Rsp {
    let scan : RspReceipt
    let account : Account? = nil
    let isRunning : Bool = false
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "account" : account?.toResultData() as Any,
            "scan" : scan.toPluginCallResultData(),
            "isRunning" : isRunning
        ]
            
    }
    
}
