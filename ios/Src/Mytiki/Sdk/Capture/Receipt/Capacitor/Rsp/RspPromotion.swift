/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct  RspPromotion : Rsp {
    private let id: Long
    private let slug: String?
    private let reward: String?
    private let rewardCurrency: String?
    private let errorCode: Int
    private let errorMessage: String?
    private let relatedProductIndexes: List<Int>
    private let qualifications: List<List<Int>>
    
    
    init (promotion: Promotion) {
        id = promotion.id()
        slug = promotion.slug()
        reward = promotion.reward()?.toPlainString()
        rewardCurrency = promotion.rewardCurrency()
        errorCode = promotion.errorCode()
        errorMessage = promotion.errorMessage()
        relatedProductIndexes = promotion.relatedProductIndexes() ?? emptyList()
        qualifications = promotion.qualifications() ?? emptyList()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("id", id)
        JSObject.updateValue("slug", slug)
        JSObject.updateValue("reward", reward)
        JSObject.updateValue("rewardCurrency", rewardCurrency)
        JSObject.updateValue("errorCode", errorCode)
        JSObject.updateValue("errorMessage", errorMessage)
        JSObject.updateValue("relatedProductIndexes", JSONArray(relatedProductIndexes))
        JSObject.updateValue("qualifications", JSArray(qualifications.map { q -> JSONArray(q) }))
    }
}
