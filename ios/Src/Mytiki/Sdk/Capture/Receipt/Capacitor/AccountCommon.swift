/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

public struct AccountCommon {
    let type: AccountTypeEnum?
    let source: String?
}

public enum AccountAlgo : AccountCommon {
    case AMAZON = AccountCommon(type: .retailer, source: RetailerCommon.AMAZON_BETA.rawValue)
    case GMAIL = AccountCommon(type: .email, source: "GMAIL")
    
}
