//
//  RspInitialized.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

struct RspInitialized : Rsp {
    private let isInitialized: Bool

    init(isInitialized: Bool){
        isInitialized = isInitialized
    }
    func toJson() -> JSObject {
        JSObject.updateValue("isInitialized", isInitialized)
    }
}
