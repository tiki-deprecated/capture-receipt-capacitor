/*
 * RspScan Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

/**
 Represents a response containing scan information.

 This struct is used to convey details about a scan, including the scanned receipt, whether the scan is running, and additional account information if available.
 */
public struct RspScan{
    var requestId: String 
    var event: PluginEvent
    /// The scanned receipt information.
    let scan: RspReceipt
    /// Indicates whether the scan is currently running.
    let isRunning: Bool = false
    /// Additional account information, if available.
    var account: Account? = nil
    
    /**
     Initializes an `RspScan` struct.

     - Parameters:
        - scan: The scanned receipt information.
        - account: Additional account information (optional, default is `nil`).
     */
    public init(requestId: String, event: PluginEvent, scan: RspReceipt, account: Account? = nil) {
        self.scan = scan
        self.account = account
        self.requestId = requestId
        self.event = event
    }
    /**
     Converts the `RspScan` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing scan information in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "account" : account?.toResultData() as Any,
            "scan" : scan.toPluginCallResultData(),
            "isRunning" : isRunning
        ]
            
    }
    
}
