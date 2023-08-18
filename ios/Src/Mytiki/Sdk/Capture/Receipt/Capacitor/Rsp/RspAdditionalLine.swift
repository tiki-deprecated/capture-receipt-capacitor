//
//  RspAdditionalLine.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor
import BlinkReceipt
import BlinkEReceipt

class RspAdditionalLine : Rsp {
    private let type: BRProductAdditionalLine
    private let text: String
    private let lineNumber: Int
    
    init (additionalLine: BRProductAdditionalLine){
        type = additionalLine.type ?? ""
        text = additionalLine.text ?? ""
        lineNumber = additionalLine.lineNumber
    }
    
    func toJson() -> JSObject {
        JSObject.pupdateValueut("type", type)
        JSObject.updateValue("text", text)
        JSObject.updateValue("lineNumber", lineNumber)
    }
}
