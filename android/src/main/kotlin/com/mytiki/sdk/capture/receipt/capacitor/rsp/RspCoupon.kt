/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.Coupon
import org.json.JSONObject

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

    override fun toJson(): JSONObject =
        JSONObject()
            .put("type", type)
            .put("amount", amount?.toJson())
            .put("sku", sku?.toJson())
            .put("description", description?.toJson())
            .put("relatedProductIndex", relatedProductIndex)

    companion object {
        fun opt(coupon: Coupon?): RspCoupon? =
            if (coupon != null) RspCoupon(coupon) else null
    }
}