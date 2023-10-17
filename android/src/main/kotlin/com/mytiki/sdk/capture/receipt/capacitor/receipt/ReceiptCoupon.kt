/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.getcapacitor.JSObject
import com.microblink.core.Coupon
import org.json.JSONObject

/**
 * Represents a parsed coupon extracted from a receipt during the RSP (Receipt Scanning Processor) process.
 *
 * @property coupon The [Coupon] object containing coupon information.
 */
class ReceiptCoupon(coupon: Coupon) {
    private val type: String?
    private val amount: ReceiptFloatType?
    private val sku: ReceiptStringType?
    private val description: ReceiptStringType?
    private val relatedProductIndex: Int

    init {
        type = coupon.typeToString()
        amount = ReceiptFloatType.opt(coupon.amount())
        sku = ReceiptStringType.opt(coupon.sku())
        description = ReceiptStringType.opt(coupon.description())
        relatedProductIndex = coupon.relatedProductIndex()
    }

    /**
     * Converts the [ReceiptCoupon] object to a JSON representation.
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
         * Creates an optional [ReceiptCoupon] object from a [Coupon].
         *
         * @param coupon The [Coupon] object to convert.
         * @return An [ReceiptCoupon] object if [coupon] is not null, otherwise null.
         */
        fun opt(coupon: Coupon?): ReceiptCoupon? =
            if (coupon != null) ReceiptCoupon(coupon) else null
    }
}
