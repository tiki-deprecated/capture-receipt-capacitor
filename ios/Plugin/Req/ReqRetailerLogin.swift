/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

public class ReqRetailerLogin {
    var username: String
    var password: String
    var retailer: String
    
    init (data: JSObject){
        username = data.getString("username") ?? ""
        password = data.getString("password") ?? ""
        retailer = data.getString("retailer") ?? ""
    }
}
