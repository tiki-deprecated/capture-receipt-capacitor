/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import Capacitor

public enum RspErrorEnum: Int {
    case ERROR = 0
    case GmailIMAPDisabled = 6
    
    public func fromInteger(_ code: Int) -> RspErrorEnum{
        switch code {
        case 0:
            return .ERROR
        case 6:
            return .GmailIMAPDisabled
        default:
            return .ERROR
        }
    }
}
