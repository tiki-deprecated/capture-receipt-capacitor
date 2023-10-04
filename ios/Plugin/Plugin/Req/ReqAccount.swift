/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

public class ReqAccount : Req {
    let accountCommon: AccountCommon
    let username: String
    let password: String?
    let isVerified: Bool?
    
    override init(_ call: CAPPluginCall) throws {
        if(AccountCommon.defaults[call.getString("id") ?? ""] == nil){
            accountCommon = AccountCommon(type: .none, source: "")
        }else{
            accountCommon = AccountCommon.defaults[call.getString("id") ?? ""]!
        }
        username = call.getString("username") ?? ""
        password = call.getString("password")
        isVerified = call.getBool("isVerified")
        try super.init(call)
    }
}
