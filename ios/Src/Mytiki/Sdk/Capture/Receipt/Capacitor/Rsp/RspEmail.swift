//
//  RspEmail.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

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
