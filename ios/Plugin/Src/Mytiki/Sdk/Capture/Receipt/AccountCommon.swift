/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

public struct AccountCommon{
    
    let type: AccountTypeEnum
    let source: String
    
    static let defaults: [String: AccountCommon] = [
        "AMAZON" : AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_BETA.rawValue),
        "amazonBeta" : AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_BETA.rawValue),
        "amazon" : AccountCommon(type: .retailer, source: RetailerEnum.AMAZON_BETA.rawValue),
        "gmail" : AccountCommon(type: .email, source: EmailEnum.GMAIL.rawValue)
    ]
}
