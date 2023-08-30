//
//  RspScan.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 30/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

public struct RspScan : Rsp {
    let _account : Account
    let _scan : RspReceipt
    let _isRunning : Bool
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "account" : (_account != nil) ? _account.toResultData() : nil,
            "physical" : _scan.toPluginCallResultData()
        ]
    }
    
}
