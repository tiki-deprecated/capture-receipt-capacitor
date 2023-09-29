/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.microblink.core.Coupon
import org.json.JSONObject

/**
 * Represents a parsed coupon extracted from a receipt during the RSP (Receipt Scanning Processor) process.
 *
 * @property coupon The [Coupon] object containing coupon information.
 */
class RspCoupon(coupon: Coupon) : Rsp {
    private val type: String?
    private val amount: RspFloatType?
    private val sku: RspStringType?
    private val description: RspStringType?
    private val relatedProductIndex: Int

    init {
        type = coupon.typeToString()
        amount = RspFloatType.opt(coupon.amount())
        sku = RspStringType.opt(coupon.sku())
        description = RspStringType.opt(coupon.description())
        relatedProductIndex = coupon.relatedProductIndex()
    }

    /**
     * Converts the [RspCoupon] object to a JSON representation.
     *
     * @return A [JSONObject] containing the coupon information.
     */
    override fun toJS(): JSObject =
        JSObject()
            .put("type", type)
            .put("amount", amount?.toJS() ?: JSObject())
            .put("sku", sku?.toJS())
            .put("description", description?.toJS())
            .put("relatedProductIndex", relatedProductIndex)

    companion object {
        /**
         * Creates an optional [RspCoupon] object from a [Coupon].
         *
         * @param coupon The [Coupon] object to convert.
         * @return An [RspCoupon] object if [coupon] is not null, otherwise null.
         */
        fun opt(coupon: Coupon?): RspCoupon? =
            if (coupon != null) RspCoupon(coupon) else null
    }
}
