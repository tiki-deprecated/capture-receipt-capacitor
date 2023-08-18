/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

struct RspEmail : Rsp {
    private let login: RspLogin
    private let scans: List<RspScan>
    
    init(credential: BREmailAccount, scans: List<RspScan>) {
        self.login = RspLogin(credential.email, credential.provider)
        self.scans = results.map({ scan in RspScan(scan)})
    }
}
