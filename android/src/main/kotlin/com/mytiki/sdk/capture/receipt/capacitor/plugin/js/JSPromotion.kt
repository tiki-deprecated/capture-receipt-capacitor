/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.js

import com.getcapacitor.JSObject
import com.microblink.core.Promotion
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a promotional item as a response in the TIKI SDK.
 *
 * @property promotion The [Promotion] object to convert into an JSPromotion.
 */
class JSPromotion(promotion: Promotion) {
    private val id: Long
    private val slug: String?
    private val reward: Double?
    private val rewardCurrency: String?
    private val errorCode: Int
    private val errorMessage: String?
    private val relatedProductIndexes: List<Int>
    private val qualifications: List<List<Int>>

    init {
        id = promotion.id()
        slug = promotion.slug()
        reward = promotion.reward()?.toDouble()
        rewardCurrency = promotion.rewardCurrency()
        errorCode = promotion.errorCode()
        errorMessage = promotion.errorMessage()
        relatedProductIndexes = promotion.relatedProductIndexes() ?: emptyList()
        qualifications = promotion.qualifications() ?: emptyList()
    }

    /**
     * Converts the JSPromotion object into a JSON representation.
     *
     * @return A [JSONObject] containing the JSON representation of the JSPromotion.
     */
    fun toJS(): JSObject =
        JSObject()
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
         * Converts a [Promotion] object into an [JSPromotion] object.
         *
         * @param promotion The [Promotion] object to convert.
         * @return An [JSPromotion] object or null if the input is null.
         */
        fun opt(promotion: Promotion?): JSPromotion? =
            if (promotion != null) JSPromotion(promotion) else null
    }
}
