/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct  RspPromotion : Rsp {
    
    private let slug: String?
    private let reward: Float?
    private let rewardCurrency: String?
    private let errorCode: Int
    private let errorMessage: String?
    private let relatedProductIndexes: [NSNumber]
    private let qualifications: [[NSNumber]]
    
    
    init (promotion: BRPromotion) {
        slug = promotion.slug
        reward = promotion.rewardValue
        rewardCurrency = promotion.rewardCurrency
        errorCode = promotion.errorCode
        errorMessage = promotion.errorMessage
        relatedProductIndexes = promotion.relatedProductIndexes
        qualifications = promotion.qualifications
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()    
        ret["slug"] = slug
        ret["reward"] = reward
        ret["rewardCurrency"] = rewardCurrency
        ret["errorCode"] = errorCode
        ret["errorMessage"] = errorMessage
        ret["relatedProductIndexes"] = JSArray(relatedProductIndexes)
        ret["qualifications"] = JSArray(qualifications.map { q in JSArray(q) })
        return ret
    }
}
