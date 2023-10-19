/*
 * RspPromotion Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing promotion information.

 This struct is used to convey details about promotions, including the promotion's slug, reward amount, error code, error message, related product indexes, and qualifications.
 */
struct JSPromotion {
    /// The slug of the promotion, if available.
    private let slug: String?
    /// The reward amount associated with the promotion, if available.
    private let reward: Float?
    /// The currency of the reward amount, if available.
    private let rewardCurrency: String?
    /// The error code associated with the promotion.
    private let errorCode: Int
    /// The error message associated with the promotion, if available.
    private let errorMessage: String?
    /// Indexes of related products associated with the promotion.
    private let relatedProductIndexes: [NSNumber]
    /// Qualifications for the promotion.
    private let qualifications: [[NSNumber]]

    /**
     Initializes an `RspPromotion` struct.

     - Parameters:
        - promotion: The `BRPromotion` object containing promotion information.
     */
    init(promotion: BRPromotion) {
        slug = promotion.slug
        reward = promotion.rewardValue
        rewardCurrency = promotion.rewardCurrency
        errorCode = promotion.errorCode
        errorMessage = promotion.errorMessage
        relatedProductIndexes = promotion.relatedProductIndexes
        qualifications = promotion.qualifications
    }
    
    static func opt(promotion: BRPromotion?) -> JSPromotion? {
        return promotion != nil ? JSPromotion(promotion: promotion!) : nil
    }

    /**
     Converts the `RspPromotion` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing promotion information in a format suitable for a Capacitor plugin call result.
     */
    func toJSObject() -> JSObject {
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
