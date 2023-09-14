/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public class ReqLogout {
    var username: String
    var password: String
    var source: String
    
    init (data: CAPPluginCall){
        username = data.getString("username") ?? ""
        password = data.getString("password") ?? ""
        source = data.getString("account_type") ?? ""
    }
}
