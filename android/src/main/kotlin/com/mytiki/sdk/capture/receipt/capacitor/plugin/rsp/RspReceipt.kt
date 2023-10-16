/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.plugin.PluginEvent
import com.mytiki.sdk.capture.receipt.capacitor.receipt.Receipt
import org.json.JSONArray

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class RspReceipt(requestId: String, private val receipt: Receipt) : Rsp(requestId, PluginEvent.onReceipt) {

    /**
     * Converts the RSP receipt data to a JSON object.
     *
     * @return A JSONObject containing the RSP receipt data.
     */
    override fun toJS(): JSObject = super.toJS()
        .put("payload", JSObject()
            .put("receiptDate", receipt.receiptDate?.toJS())
            .put("receiptTime", receipt.receiptTime?.toJS())
            .put("retailerId", receipt.retailerId.toJS())
            .put("products", JSONArray(receipt.products.map { prd -> prd.toJS() }))
            .put("coupons", JSONArray(receipt.coupons.map { coupon -> coupon.toJS() }))
            .put("total", receipt.total?.toJS())
            .put("tip", receipt.tip?.toJS())
            .put("subtotal", receipt.subtotal?.toJS())
            .put("taxes", receipt.taxes?.toJS())
            .put("storeNumber", receipt.storeNumber?.toJS())
            .put("merchantName", receipt.merchantName?.toJS())
            .put("storeAddress", receipt.storeAddress?.toJS())
            .put("storeCity", receipt.storeCity?.toJS())
            .put("blinkReceiptId", receipt.blinkReceiptId)
            .put("storeState", receipt.storeState?.toJS())
            .put("storeZip", receipt.storeZip?.toJS())
            .put("storeCountry", receipt.storeCountry?.toJS())
            .put("storePhone", receipt.storePhone?.toJS())
            .put("cashierId", receipt.cashierId?.toJS())
            .put("transactionId", receipt.transactionId?.toJS())
            .put("registerId", receipt.registerId?.toJS())
            .put("paymentMethods", JSONArray(receipt.paymentMethods.map { method -> method.toJS() }))
            .put("taxId", receipt.taxId?.toJS())
            .put("mallName", receipt.mallName?.toJS())
            .put("last4cc", receipt.last4cc?.toJS())
            .put("ocrConfidence", receipt.ocrConfidence)
            .put("merchantSource", receipt.merchantSource)
            .put("foundTopEdge", receipt.foundTopEdge)
            .put("foundBottomEdge", receipt.foundBottomEdge)
            .put("eReceiptOrderNumber", receipt.eReceiptOrderNumber)
            .put("eReceiptOrderStatus", receipt.eReceiptOrderStatus)
            .put("eReceiptRawHtml", receipt.eReceiptRawHtml)
            .put("eReceiptShippingAddress", receipt.eReceiptShippingAddress)
            .put("shipments", JSONArray(receipt.shipments.map { shipment -> shipment.toJS() }))
            .put("longTransactionId", receipt.longTransactionId?.toJS())
            .put("subtotalMatches", receipt.subtotalMatches)
            .put("eReceiptEmailProvider", receipt.eReceiptEmailProvider)
            .put("eReceiptEmailId", receipt.eReceiptEmailId)
            .put("eReceiptAuthenticated", receipt.eReceiptAuthenticated)
            .put("instacartShopper", receipt.instacartShopper)
            .put("eReceipt", receipt.eReceipt)
            .put(
                "eReceiptComponentEmails",JSONArray(receipt.eReceiptComponentEmails.map { email -> RspReceipt(requestId, email).toJS() })
            )
            .put("duplicate", receipt.duplicate)
            .put("fraudulent", receipt.fraudulent)
            .put("receiptDateTime", receipt.receiptDateTime)
            .put("duplicateBlinkReceiptIds", JSONArray(receipt.duplicateBlinkReceiptIds))
            .put("merchantMatchGuess", receipt.merchantMatchGuess?.toJS())
            .put("productsPendingLookup", receipt.productsPendingLookup)
            .put(
                "qualifiedPromotions",JSONArray(receipt.qualifiedPromotions.map { promo -> promo.toJS() })
            )
            .put(
                "unqualifiedPromotions",JSONArray(receipt.unqualifiedPromotions.map { promo -> promo.toJS() })
            )
            .put("extendedFields", receipt.extendedFields)
            .put("eReceiptAdditionalFees", receipt.eReceiptAdditionalFees)
            .put("purchaseType", receipt.purchaseType?.toJS())
            .put("loyaltyForBanner", receipt.loyaltyForBanner)
            .put("channel", receipt.channel?.toJS())
            .put("submissionDate", receipt.submissionDate)
            .put("eReceiptFulfilledBy", receipt.eReceiptFulfilledBy)
            .put("eReceiptShippingStatus", receipt.eReceiptShippingStatus)
            .put("eReceiptPOSSystem", receipt.eReceiptPOSSystem)
            .put("eReceiptSubMerchant", receipt.eReceiptSubMerchant)
            .put(
                "qualifiedSurveys",JSONArray(receipt.qualifiedSurveys.map { survey -> survey.toJS() })
            )
            .put("barcode", receipt.barcode)
            .put("eReceiptMerchantEmail", receipt.eReceiptMerchantEmail)
            .put("eReceiptEmailSubject", receipt.eReceiptEmailSubject)
            .put("eReceiptShippingCosts", receipt.eReceiptShippingCosts)
            .put("currencyCode", receipt.currencyCode)
            .put("clientMerchantName", receipt.clientMerchantName?.toJS())
            .put("loyaltyProgram", receipt.loyaltyProgram)
            .put("merchantSources", JSONArray(receipt.merchantSources))
            .put("paymentTerminalId", receipt.paymentTerminalId?.toJS())
            .put("paymentTransactionId", receipt.paymentTransactionId?.toJS())
            .put("combinedRawText", receipt.combinedRawText?.toJS()))
}
