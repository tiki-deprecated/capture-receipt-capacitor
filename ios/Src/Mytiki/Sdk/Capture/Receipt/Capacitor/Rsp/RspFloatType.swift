//
//  RspFloatType.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspFloatType : Rsp{
    private let confidence: Float
    private let value: Float
    
    
    init(floatType: BRFloatValue) {
        confidence = floatType.confidence
        value = floatType.value
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("confidence", confidence)
        JSObject.updateValue("value", value)
    }
}
