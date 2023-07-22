/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.PaymentMethod
import org.json.JSONObject

class RspPaymentMethod(paymentMethod: PaymentMethod) : Rsp {
    private val paymentMethod: RspStringType?
    private val cardType: RspStringType?
    private val cardIssuer: RspStringType?
    private val amount: RspFloatType?

    init {
        this.paymentMethod = RspStringType.opt(paymentMethod.paymentMethod())
        cardType = RspStringType.opt(paymentMethod.cardType())
        cardIssuer = RspStringType.opt(paymentMethod.cardIssuer())
        amount = RspFloatType.opt(paymentMethod.amount())
    }

    override fun toJson(): JSONObject =
        JSONObject()
            .put("paymentMethod", paymentMethod?.toJson())
            .put("cardType", cardType?.toJson())
            .put("cardIssuer", cardIssuer?.toJson())
            .put("amount", amount?.toJson())

    companion object {
        fun opt(paymentMethod: PaymentMethod?): RspPaymentMethod? =
            if (paymentMethod != null) RspPaymentMethod(paymentMethod) else null
    }
}