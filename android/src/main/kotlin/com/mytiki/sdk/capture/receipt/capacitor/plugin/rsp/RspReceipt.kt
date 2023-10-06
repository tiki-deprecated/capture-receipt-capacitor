/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp

import com.getcapacitor.JSObject
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.plugin.PluginEvent
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSCoupon
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSFloatType
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSPaymentMethod
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSProduct
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSPromotion
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSRetailer
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSShipment
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSStringType
import com.mytiki.sdk.capture.receipt.capacitor.plugin.js.JSSurvey
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class RspReceipt(requestId: String, scanResults: ScanResults) : Rsp(requestId, PluginEvent.onReceipt) {

    private val receiptDate: JSStringType?
    private val receiptTime: JSStringType?
    private val retailerId: JSRetailer
    private val products: List<JSProduct>
    private val coupons: List<JSCoupon>
    private val total: JSFloatType?
    private val tip: JSFloatType?
    private val subtotal: JSFloatType?
    private val taxes: JSFloatType?
    private val storeNumber: JSStringType?
    private val merchantName: JSStringType?
    private val storeAddress: JSStringType?
    private val storeCity: JSStringType?
    private val blinkReceiptId: String?
    private val storeState: JSStringType?
    private val storeZip: JSStringType?
    private val storeCountry: JSStringType?
    private val storePhone: JSStringType?
    private val cashierId: JSStringType?
    private val transactionId: JSStringType?
    private val registerId: JSStringType?
    private val paymentMethods: List<JSPaymentMethod>
    private val taxId: JSStringType?
    private val mallName: JSStringType?
    private val last4cc: JSStringType?
    private val ocrConfidence: Float
    private val merchantSource: String?
    private val foundTopEdge: Boolean
    private val foundBottomEdge: Boolean
    private val eReceiptOrderNumber: String?
    private val eReceiptOrderStatus: String?
    private val eReceiptRawHtml: String?
    private val eReceiptShippingAddress: String?
    private val shipments: List<JSShipment>
    private val longTransactionId: JSStringType?
    private val subtotalMatches: Boolean
    private val eReceiptEmailProvider: String?
    private val eReceiptEmailId: String?
    private val eReceiptAuthenticated: Boolean
    private val instacartShopper: Boolean
    private val eReceipt: Boolean
    private val eReceiptComponentEmails: List<RspReceipt>
    private val duplicate: Boolean
    private val fraudulent: Boolean
    private val receiptDateTime: Long?
    private val duplicateBlinkReceiptIds: List<String>
    private val merchantMatchGuess: JSStringType?
    private val productsPendingLookup: Int
    private val qualifiedPromotions: List<JSPromotion>
    private val unqualifiedPromotions: List<JSPromotion>
    private val extendedFields: JSONObject?
    private val eReceiptAdditionalFees: JSONObject?
    private val purchaseType: JSStringType?
    private val loyaltyForBanner: Boolean
    private val channel: JSStringType?
    private val submissionDate: Long?
    private val eReceiptFulfilledBy: String?
    private val eReceiptShippingStatus: String?
    private val eReceiptPOSSystem: String?
    private val eReceiptSubMerchant: String?
    private val qualifiedSurveys: List<JSSurvey>
    private val barcode: String?
    private val eReceiptMerchantEmail: String?
    private val eReceiptEmailSubject: String?
    private val eReceiptShippingCosts: Float
    private val currencyCode: String?
    private val clientMerchantName: String?
    private val loyaltyProgram: Boolean
    private val merchantSources: List<Int>
    private val paymentTerminalId: JSStringType?
    private val paymentTransactionId: JSStringType?
    private val combinedRawText: JSStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = JSStringType.opt(scanResults.receiptDate())
        receiptTime = JSStringType.opt(scanResults.receiptTime())
        retailerId = JSRetailer(scanResults.retailerId())
        products = scanResults.products()?.map { product -> JSProduct(product) } ?: emptyList()
        coupons = scanResults.coupons()?.map { coupon -> JSCoupon(coupon) } ?: emptyList()
        total = JSFloatType.opt(scanResults.total())
        tip = JSFloatType.opt(scanResults.tip())
        subtotal = JSFloatType.opt(scanResults.subtotal())
        taxes = JSFloatType.opt(scanResults.taxes())
        storeNumber = JSStringType.opt(scanResults.storeNumber())
        merchantName = JSStringType.opt(scanResults.merchantName())
        storeAddress = JSStringType.opt(scanResults.storeAddress())
        storeCity = JSStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = JSStringType.opt(scanResults.storeState())
        storeZip = JSStringType.opt(scanResults.storeZip())
        storeCountry = JSStringType.opt(scanResults.storeCountry())
        storePhone = JSStringType.opt(scanResults.storePhone())
        cashierId = JSStringType.opt(scanResults.cashierId())
        transactionId = JSStringType.opt(scanResults.transactionId())
        registerId = JSStringType.opt(scanResults.registerId())
        paymentMethods =
            scanResults.paymentMethods()?.map { paymentMethod -> JSPaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = JSStringType.opt(scanResults.taxId())
        mallName = JSStringType.opt(scanResults.mallName())
        last4cc = JSStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> JSShipment(shipment) } ?: emptyList()
        longTransactionId = JSStringType.opt(scanResults.longTransactionId())
        subtotalMatches = scanResults.subtotalMatches()
        eReceiptEmailProvider = scanResults.eReceiptEmailProvider()
        eReceiptEmailId = scanResults.eReceiptEmailId()
        eReceiptAuthenticated = scanResults.eReceiptAuthenticated()
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.eReceipt()
        eReceiptComponentEmails =
            scanResults.eReceiptComponentEmails()?.map { res -> RspReceipt(requestId, res) }
                ?: emptyList()
        duplicate = scanResults.duplicate()
        fraudulent = scanResults.fraudulent()
        receiptDateTime = scanResults.receiptDateTime()?.time
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds() ?: emptyList()
        merchantMatchGuess = JSStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> JSPromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> JSPromotion(promotion) } ?: emptyList()
        extendedFields = if (scanResults.extendedFields() != null) {
            val extendedFields = JSObject()
            scanResults.extendedFields()
                ?.forEach { entry -> extendedFields.put(entry.key, entry.value) }
            extendedFields
        } else null
        eReceiptAdditionalFees = if (scanResults.eReceiptAdditionalFees() != null) {
            val additionalFees = JSObject()
            scanResults.eReceiptAdditionalFees()
                ?.forEach { entry -> additionalFees.put(entry.key, entry.value) }
            additionalFees
        } else null
        purchaseType = JSStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = JSStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> JSSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = scanResults.clientMerchantName()
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = JSStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = JSStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = JSStringType.opt(scanResults.combinedRawText())
    }

    /**
     * Converts the RSP receipt data to a JSON object.
     *
     * @return A JSONObject containing the RSP receipt data.
     */
    override fun toJS(): JSObject = super.toJS()
        .put("payload", JSObject()
            .put("receiptDate", receiptDate?.toJS())
            .put("receiptTime", receiptTime?.toJS())
            .put("retailerId", retailerId.toJS())
            .put("products", JSONArray(products.map { prd -> prd.toJS() }))
            .put("coupons", JSONArray(coupons.map { coupon -> coupon.toJS() }))
            .put("total", total?.toJS())
            .put("tip", tip?.toJS())
            .put("subtotal", subtotal?.toJS())
            .put("taxes", taxes?.toJS())
            .put("storeNumber", storeNumber?.toJS())
            .put("merchantName", merchantName?.toJS())
            .put("storeAddress", storeAddress?.toJS())
            .put("storeCity", storeCity?.toJS())
            .put("blinkReceiptId", blinkReceiptId)
            .put("storeState", storeState?.toJS())
            .put("storeZip", storeZip?.toJS())
            .put("storeCountry", storeCountry?.toJS())
            .put("storePhone", storePhone?.toJS())
            .put("cashierId", cashierId?.toJS())
            .put("transactionId", transactionId?.toJS())
            .put("registerId", registerId?.toJS())
            .put("paymentMethods", JSONArray(paymentMethods.map { method -> method.toJS() }))
            .put("taxId", taxId?.toJS())
            .put("mallName", mallName?.toJS())
            .put("last4cc", last4cc?.toJS())
            .put("ocrConfidence", ocrConfidence)
            .put("merchantSource", merchantSource)
            .put("foundTopEdge", foundTopEdge)
            .put("foundBottomEdge", foundBottomEdge)
            .put("eReceiptOrderNumber", eReceiptOrderNumber)
            .put("eReceiptOrderStatus", eReceiptOrderStatus)
            .put("eReceiptRawHtml", eReceiptRawHtml)
            .put("eReceiptShippingAddress", eReceiptShippingAddress)
            .put("shipments", JSONArray(shipments.map { shipment -> shipment.toJS() }))
            .put("longTransactionId", longTransactionId?.toJS())
            .put("subtotalMatches", subtotalMatches)
            .put("eReceiptEmailProvider", eReceiptEmailProvider)
            .put("eReceiptEmailId", eReceiptEmailId)
            .put("eReceiptAuthenticated", eReceiptAuthenticated)
            .put("instacartShopper", instacartShopper)
            .put("eReceipt", eReceipt)
            .put(
                "eReceiptComponentEmails",
                JSONArray(eReceiptComponentEmails.map { email -> email.toJS() })
            )
            .put("duplicate", duplicate)
            .put("fraudulent", fraudulent)
            .put("receiptDateTime", receiptDateTime)
            .put("duplicateBlinkReceiptIds", JSONArray(duplicateBlinkReceiptIds))
            .put("merchantMatchGuess", merchantMatchGuess?.toJS())
            .put("productsPendingLookup", productsPendingLookup)
            .put(
                "qualifiedPromotions",
                JSONArray(qualifiedPromotions.map { promo -> promo.toJS() })
            )
            .put(
                "unqualifiedPromotions",
                JSONArray(unqualifiedPromotions.map { promo -> promo.toJS() })
            )
            .put("extendedFields", extendedFields)
            .put("eReceiptAdditionalFees", eReceiptAdditionalFees)
            .put("purchaseType", purchaseType?.toJS())
            .put("loyaltyForBanner", loyaltyForBanner)
            .put("channel", channel?.toJS())
            .put("submissionDate", submissionDate)
            .put("eReceiptFulfilledBy", eReceiptFulfilledBy)
            .put("eReceiptShippingStatus", eReceiptShippingStatus)
            .put("eReceiptPOSSystem", eReceiptPOSSystem)
            .put("eReceiptSubMerchant", eReceiptSubMerchant)
            .put(
                "qualifiedSurveys",
                JSONArray(qualifiedSurveys.map { survey -> survey.toJS() })
            )
            .put("barcode", barcode)
            .put("eReceiptMerchantEmail", eReceiptMerchantEmail)
            .put("eReceiptEmailSubject", eReceiptEmailSubject)
            .put("eReceiptShippingCosts", eReceiptShippingCosts)
            .put("currencyCode", currencyCode)
            .put("clientMerchantName", clientMerchantName)
            .put("loyaltyProgram", loyaltyProgram)
            .put("merchantSources", JSONArray(merchantSources))
            .put("paymentTerminalId", paymentTerminalId?.toJS())
            .put("paymentTransactionId", paymentTransactionId?.toJS())
            .put("combinedRawText", combinedRawText?.toJS()))

    companion object {
        /**
         * Creates an optional RSP receipt from the provided scan results.
         *
         * @param scanResults The scan results to create an RSP receipt from.
         * @return An optional RSPReceipt instance, or null if scanResults is null.
         */
        fun opt(requestId: String, scanResults: ScanResults?): RspReceipt? =
            if (scanResults != null) RspReceipt(requestId, scanResults) else null
    }
}
