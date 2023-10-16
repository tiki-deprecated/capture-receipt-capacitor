/*
 * RspReceipt Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 A class representing a response containing receipt information for the ReceiptCapture plugin.
 
 This class encapsulates detailed information about a receipt, including various receipt fields such as date, time, products, coupons, totals, and more.
 */
public class RspReceipt : Rsp{
    private let receipt : Receipt
    /**
     Initializes an `RspReceipt` struct.

     - Parameters:
        - scanResults: The `BRScanResults` object containing receipt information.
     */
    init(requestId: String, receipt: Receipt) {
        self.receipt = receipt
        super.init(requestId: requestId, event: .onReceipt)
    }

    
    /**
     Converts the `RspReceipt` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing receipt information in a format suitable for a Capacitor plugin call result.
     */
    override func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = super.toPluginCallResultData()
        ret["payload"] = toJSObject()
        return ret
    }
    
    func toJSObject() -> JSObject {
        var payload = receipt.toJSObject()
        return payload
    }
}
