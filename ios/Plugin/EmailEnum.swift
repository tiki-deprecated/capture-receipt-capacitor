/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

public enum EmailEnum: String, CaseIterable {
    case GMAIL
    case AOL
    case YAHOO
    case OUTLOOK

    func toBREReceiptProvider() -> BREReceiptProvider? {
        switch self{
            case .AOL : return .AOL
            case .GMAIL : return .gmailIMAP
            case .OUTLOOK : return .outlook
            case .YAHOO : return .yahoo
        }
    }
}
