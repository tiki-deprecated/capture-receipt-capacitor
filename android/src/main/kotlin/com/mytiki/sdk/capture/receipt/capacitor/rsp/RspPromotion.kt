/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Promotion
import org.json.JSONObject

class RspPromotion(promotion: Promotion) : Rsp {
    private val id: Long
    private val slug: String?
    private val reward: String?
    private val rewardCurrency: String?
    private val errorCode: Int
    private val errorMessage: String?
    private val relatedProductIndexes: List<Int>?
    private val qualifications: List<List<Int>>?

    init {
        id = promotion.id()
        slug = promotion.slug()
        reward = promotion.reward()?.toPlainString()
        rewardCurrency = promotion.rewardCurrency()
        errorCode = promotion.errorCode()
        errorMessage = promotion.errorMessage()
        relatedProductIndexes = promotion.relatedProductIndexes()
        qualifications = promotion.qualifications()
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("slug", slug)
            .put("reward", reward)
            .put("rewardCurrency", rewardCurrency)
            .put("errorCode", errorCode)
            .put("errorMessage", errorMessage)
            .put("relatedProductIndexes", relatedProductIndexes)
            .put("qualifications", qualifications)

    companion object {
        fun opt(promotion: Promotion?): RspPromotion? =
            if (promotion != null) RspPromotion(promotion) else null
    }
}