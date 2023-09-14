/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */


import Foundation
import Capacitor

public struct ReqScan {
    // The type of scan to perform, as specified by [ScanTypeEnum].
    let scanType: ScanTypeEnum?
    // The optional [Account] associated with the scan request, or `null` if not provided.
    let account: Account?
    
    init(data: CAPPluginCall) {
        guard let scanType = ScanTypeEnum.allCases.first(where: { value in value.rawValue == data.getString("scanType")}) else {
            data.reject("Invalid ScanType")
            self.account = nil
            self.scanType = nil
            return
            }
        self.scanType = scanType
        guard let source = data.getString("source") else {
            self.account = nil
            return
        }
        self.account = Account(accountType: AccountCommon.defaults[source]!, user: data.getString("user") ?? "", password: data.getString("password"), isVerified: data.getBool("isVerified"))
        
    }
}
