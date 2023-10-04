/*
 * EmailEnum Enum
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt

/// An enumeration representing different email providers.
public enum EmailEnum: String, CaseIterable {
    
    /// Represents the Gmail email provider.
    case GMAIL

    /// Represents the AOL email provider.
    case AOL
    
    /// Represents the Yahoo email provider.
    case YAHOO
    
    /// Represents the Outlook email provider.
    case OUTLOOK

    /// Converts an EmailEnum case to the corresponding BREReceiptProvider.
    ///
    /// - Returns: A BREReceiptProvider corresponding to the EmailEnum case, or nil if not found.
    func toBREReceiptProvider() -> BREReceiptProvider? {
        switch self{
            case .AOL : return .AOL
            case .GMAIL : return .gmailIMAP
            case .OUTLOOK : return .outlook
            case .YAHOO : return .yahoo
        }
    }
}
