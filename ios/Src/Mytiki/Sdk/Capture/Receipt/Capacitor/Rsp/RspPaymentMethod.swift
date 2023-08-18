//
//  RspPaymentMethod.swift
//  Plugin
//
//  Created by Jesse Monteiro Ferreira on 18/08/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

struct RspPaymentMethod : Rsp {
    private let paymentMethod: RspStringTyp?
    private let cardType: RspStringType?
    private let cardIssuer: RspStringType?
    private let amount: RspFloatType?
    
    init (paymentMethod: PaymentMethod)
}
