/*
 * ReqRetailerLogin Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

public struct ReqAccount {
    let accountCommon: AccountCommon
    let username: String
    let password: String?
    let isVerified: Bool?
    
    init(data: CAPPluginCall){
        if(AccountCommon.defaults[data.getString("source") ?? ""] == nil){
            data.reject("Invalid Source")
        }
        accountCommon = AccountCommon.defaults[data.getString("source") ?? ""]!
        username = data.getString("username") ?? ""
        password = data.getString("password")
        isVerified = data.getBool("isVerified")
    }
}
