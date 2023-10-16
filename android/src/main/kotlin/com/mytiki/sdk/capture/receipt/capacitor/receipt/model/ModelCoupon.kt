/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt.model

import com.getcapacitor.JSObject
import com.microblink.core.Coupon
import org.json.JSONObject

/**
 * Represents a parsed coupon extracted from a receipt during the RSP (Receipt Scanning Processor) process.
 *
 * @property coupon The [Coupon] object containing coupon information.
 */
class ModelCoupon(coupon: Coupon) {
    private val type: String?
    private val amount: ModelFloatType?
    private val sku: ModelStringType?
    private val description: ModelStringType?
    private val relatedProductIndex: Int

    init {
        type = coupon.typeToString()
        amount = ModelFloatType.opt(coupon.amount())
        sku = ModelStringType.opt(coupon.sku())
        description = ModelStringType.opt(coupon.description())
        relatedProductIndex = coupon.relatedProductIndex()
    }

    /**
     * Converts the [ModelCoupon] object to a JSON representation.
     *
     * @return A [JSONObject] containing the coupon information.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("type", type)
            .put("amount", amount?.toJS() ?: JSObject())
            .put("sku", sku?.toJS())
            .put("description", description?.toJS())
            .put("relatedProductIndex", relatedProductIndex)

    companion object {
        /**
         * Creates an optional [ModelCoupon] object from a [Coupon].
         *
         * @param coupon The [Coupon] object to convert.
         * @return An [ModelCoupon] object if [coupon] is not null, otherwise null.
         */
        fun opt(coupon: Coupon?): ModelCoupon? =
            if (coupon != null) ModelCoupon(coupon) else null
    }
}
