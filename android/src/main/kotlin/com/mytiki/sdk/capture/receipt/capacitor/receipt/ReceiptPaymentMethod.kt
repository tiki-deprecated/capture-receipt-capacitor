/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

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
class ReceiptPaymentMethod(paymentMethod: PaymentMethod) {
    private val paymentMethod: ReceiptStringType?
    private val cardType: ReceiptStringType?
    private val cardIssuer: ReceiptStringType?
    private val amount: ReceiptFloatType?

    init {
        this.paymentMethod = ReceiptStringType.opt(paymentMethod.paymentMethod())
        cardType = ReceiptStringType.opt(paymentMethod.cardType())
        cardIssuer = ReceiptStringType.opt(paymentMethod.cardIssuer())
        amount = ReceiptFloatType.opt(paymentMethod.amount())
    }

    /**
     * Converts the [ReceiptPaymentMethod] instance to a JSON representation.
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
         * Creates an [ReceiptPaymentMethod] instance from a [PaymentMethod] instance,
         * or returns null if the input is null.
         *
         * @param paymentMethod The [PaymentMethod] instance to be converted.
         * @return An [ReceiptPaymentMethod] instance if [paymentMethod] is not null; otherwise, null.
         */
        fun opt(paymentMethod: PaymentMethod?): ReceiptPaymentMethod? =
            if (paymentMethod != null) ReceiptPaymentMethod(paymentMethod) else null
    }
}
