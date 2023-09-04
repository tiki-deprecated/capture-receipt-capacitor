/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Promotion
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a promotional item as a response in the TIKI SDK.
 *
 * @property promotion The [Promotion] object to convert into an RspPromotion.
 */
class RspPromotion(promotion: Promotion) : Rsp {
    private val id: Long
    private val slug: String?
    private val reward: String?
    private val rewardCurrency: String?
    private val errorCode: Int
    private val errorMessage: String?
    private val relatedProductIndexes: List<Int>
    private val qualifications: List<List<Int>>

    init {
        id = promotion.id()
        slug = promotion.slug()
        reward = promotion.reward()?.toPlainString()
        rewardCurrency = promotion.rewardCurrency()
        errorCode = promotion.errorCode()
        errorMessage = promotion.errorMessage()
        relatedProductIndexes = promotion.relatedProductIndexes() ?: emptyList()
        qualifications = promotion.qualifications() ?: emptyList()
    }

    /**
     * Converts the RspPromotion object into a JSON representation.
     *
     * @return A [JSONObject] containing the JSON representation of the RspPromotion.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("slug", slug)
            .put("reward", reward)
            .put("rewardCurrency", rewardCurrency)
            .put("errorCode", errorCode)
            .put("errorMessage", errorMessage)
            .put("relatedProductIndexes", JSONArray(relatedProductIndexes))
            .put("qualifications", JSONArray(qualifications.map { q -> JSONArray(q) }))

    companion object {
        /**
         * Converts a [Promotion] object into an [RspPromotion] object.
         *
         * @param promotion The [Promotion] object to convert.
         * @return An [RspPromotion] object or null if the input is null.
         */
        fun opt(promotion: Promotion?): RspPromotion? =
            if (promotion != null) RspPromotion(promotion) else null
    }
}
