/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public struct RspScan : Rsp {
    let scan : RspReceipt
    let isRunning : Bool = false
    var account : Account? = nil
    
    public init(scan: RspReceipt, account: Account? = nil) {
        self.scan = scan
        self.account = account
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "account" : account?.toResultData() as Any,
            "scan" : scan.toPluginCallResultData(),
            "isRunning" : isRunning
        ]
            
    }
    
}
