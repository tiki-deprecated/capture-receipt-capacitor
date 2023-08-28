/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

public struct AccountCommon{
    
    let type: AccountTypeCommon
    let source: String
    
    static let defaults: [String: AccountCommon] = [
        "AMAZON" : AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_BETA.rawValue),
        "GMAIL" : AccountCommon(type: .email, source: "GMAIL")
    ]
}
