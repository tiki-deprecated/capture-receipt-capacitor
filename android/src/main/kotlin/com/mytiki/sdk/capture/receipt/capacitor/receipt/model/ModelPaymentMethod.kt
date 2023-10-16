/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt.model

import com.getcapacitor.JSObject
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
class ModelPaymentMethod(paymentMethod: PaymentMethod) {
    private val paymentMethod: ModelStringType?
    private val cardType: ModelStringType?
    private val cardIssuer: ModelStringType?
    private val amount: ModelFloatType?

    init {
        this.paymentMethod = ModelStringType.opt(paymentMethod.paymentMethod())
        cardType = ModelStringType.opt(paymentMethod.cardType())
        cardIssuer = ModelStringType.opt(paymentMethod.cardIssuer())
        amount = ModelFloatType.opt(paymentMethod.amount())
    }

    /**
     * Converts the [ModelPaymentMethod] instance to a JSON representation.
     *
     * @return A [JSONObject] containing the payment method information.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("paymentMethod", paymentMethod?.toJS())
            .put("cardType", cardType?.toJS())
            .put("cardIssuer", cardIssuer?.toJS())
            .put("amount", amount?.toJS())

    companion object {
        /**
         * Creates an [ModelPaymentMethod] instance from a [PaymentMethod] instance,
         * or returns null if the input is null.
         *
         * @param paymentMethod The [PaymentMethod] instance to be converted.
         * @return An [ModelPaymentMethod] instance if [paymentMethod] is not null; otherwise, null.
         */
        fun opt(paymentMethod: PaymentMethod?): ModelPaymentMethod? =
            if (paymentMethod != null) ModelPaymentMethod(paymentMethod) else null
    }
}
