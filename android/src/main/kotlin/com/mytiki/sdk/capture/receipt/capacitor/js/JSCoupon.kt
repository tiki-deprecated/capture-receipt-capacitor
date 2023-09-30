/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.js

import com.getcapacitor.JSObject
import com.microblink.core.Coupon
import org.json.JSONObject

/**
 * Represents a parsed coupon extracted from a receipt during the RSP (Receipt Scanning Processor) process.
 *
 * @property coupon The [Coupon] object containing coupon information.
 */
class JSCoupon(coupon: Coupon) {
    private val type: String?
    private val amount: JSFloatType?
    private val sku: JSStringType?
    private val description: JSStringType?
    private val relatedProductIndex: Int

    init {
        type = coupon.typeToString()
        amount = JSFloatType.opt(coupon.amount())
        sku = JSStringType.opt(coupon.sku())
        description = JSStringType.opt(coupon.description())
        relatedProductIndex = coupon.relatedProductIndex()
    }

    /**
     * Converts the [JSCoupon] object to a JSON representation.
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
         * Creates an optional [JSCoupon] object from a [Coupon].
         *
         * @param coupon The [Coupon] object to convert.
         * @return An [JSCoupon] object if [coupon] is not null, otherwise null.
         */
        fun opt(coupon: Coupon?): JSCoupon? =
            if (coupon != null) JSCoupon(coupon) else null
    }
}
