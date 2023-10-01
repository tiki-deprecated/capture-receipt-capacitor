/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.js

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
class JSPaymentMethod(paymentMethod: PaymentMethod) {
    private val paymentMethod: JSStringType?
    private val cardType: JSStringType?
    private val cardIssuer: JSStringType?
    private val amount: JSFloatType?

    init {
        this.paymentMethod = JSStringType.opt(paymentMethod.paymentMethod())
        cardType = JSStringType.opt(paymentMethod.cardType())
        cardIssuer = JSStringType.opt(paymentMethod.cardIssuer())
        amount = JSFloatType.opt(paymentMethod.amount())
    }

    /**
     * Converts the [JSPaymentMethod] instance to a JSON representation.
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
         * Creates an [JSPaymentMethod] instance from a [PaymentMethod] instance,
         * or returns null if the input is null.
         *
         * @param paymentMethod The [PaymentMethod] instance to be converted.
         * @return An [JSPaymentMethod] instance if [paymentMethod] is not null; otherwise, null.
         */
        fun opt(paymentMethod: PaymentMethod?): JSPaymentMethod? =
            if (paymentMethod != null) JSPaymentMethod(paymentMethod) else null
    }
}
