/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public class Scan {
    func initialize(req: ReqInitialize){
        BRScanManager.shared().licenseKey = req.licenseKey
        BRScanManager.shared().prodIntelKey = req.productKey
        BRAccountLinkingManager.shared()
    }
}



