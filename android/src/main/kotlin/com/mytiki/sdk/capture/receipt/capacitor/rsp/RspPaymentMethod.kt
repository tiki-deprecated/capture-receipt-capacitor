/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.PaymentMethod
import org.json.JSONObject

/**
 * Represents a Receipt Scanning Plugin Payment Method for TIKI SDK.
 *
 * This class wraps the [PaymentMethod] class provided by MicroBlink
 * and provides a structured representation for payment-related information
 * extracted from a receipt.
 *
 * @param paymentMethod The [PaymentMethod] instance to be wrapped.
 */
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

    /**
     * Converts the [RspPaymentMethod] instance to a JSON representation.
     *
     * @return A [JSONObject] containing the payment method information.
     */
    override fun toJson(): JSONObject =
        JSONObject()
            .put("paymentMethod", paymentMethod?.toJson())
            .put("cardType", cardType?.toJson())
            .put("cardIssuer", cardIssuer?.toJson())
            .put("amount", amount?.toJson())

    companion object {
        /**
         * Creates an [RspPaymentMethod] instance from a [PaymentMethod] instance,
         * or returns null if the input is null.
         *
         * @param paymentMethod The [PaymentMethod] instance to be converted.
         * @return An [RspPaymentMethod] instance if [paymentMethod] is not null; otherwise, null.
         */
        fun opt(paymentMethod: PaymentMethod?): RspPaymentMethod? =
            if (paymentMethod != null) RspPaymentMethod(paymentMethod) else null
    }
}
