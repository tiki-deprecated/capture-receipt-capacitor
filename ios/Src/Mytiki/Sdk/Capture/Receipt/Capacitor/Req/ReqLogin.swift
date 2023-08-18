/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public class ReqLogin {
    var username: String
    var password: String
    var provider: String
    
    init (data: JSObject){
        username = data.getString("username") ?? ""
        password = data.getString("password") ?? ""
        provider = data.getString("provider") ?? ""
    }
}
