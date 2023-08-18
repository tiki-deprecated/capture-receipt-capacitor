//
//  ReqLogin.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

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
