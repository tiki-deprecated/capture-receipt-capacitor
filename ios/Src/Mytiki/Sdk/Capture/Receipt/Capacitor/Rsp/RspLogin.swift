//
//  RspLogin.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

struct RspLogin : Rsp {
    private let username: String
    private let provider: String
    
    func toJson() -> JSObject {
        JSObject.updateValue("username", username)
        JSObject.updateValue("provider", provider)
    }
    
}
