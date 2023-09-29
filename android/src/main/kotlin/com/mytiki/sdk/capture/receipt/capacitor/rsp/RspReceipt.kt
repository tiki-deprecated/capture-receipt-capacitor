/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.getcapacitor.JSObject
import com.microblink.core.ScanResults
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class RspReceipt(scanResults: ScanResults) : Rsp {

    private val receiptDate: RspStringType?
    private val receiptTime: RspStringType?
    private val retailerId: RspRetailer
    private val products: List<RspProduct>
    private val coupons: List<RspCoupon>
    private val total: RspFloatType?
    private val tip: RspFloatType?
    private val subtotal: RspFloatType?
    private val taxes: RspFloatType?
    private val storeNumber: RspStringType?
    private val merchantName: RspStringType?
    private val storeAddress: RspStringType?
    private val storeCity: RspStringType?
    private val blinkReceiptId: String?
    private val storeState: RspStringType?
    private val storeZip: RspStringType?
    private val storeCountry: RspStringType?
    private val storePhone: RspStringType?
    private val cashierId: RspStringType?
    private val transactionId: RspStringType?
    private val registerId: RspStringType?
    private val paymentMethods: List<RspPaymentMethod>
    private val taxId: RspStringType?
    private val mallName: RspStringType?
    private val last4cc: RspStringType?
    private val ocrConfidence: Float
    private val merchantSource: String?
    private val foundTopEdge: Boolean
    private val foundBottomEdge: Boolean
    private val eReceiptOrderNumber: String?
    private val eReceiptOrderStatus: String?
    private val eReceiptRawHtml: String?
    private val eReceiptShippingAddress: String?
    private val shipments: List<RspShipment>
    private val longTransactionId: RspStringType?
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
    private val merchantMatchGuess: RspStringType?
    private val productsPendingLookup: Int
    private val qualifiedPromotions: List<RspPromotion>
    private val unqualifiedPromotions: List<RspPromotion>
    private val extendedFields: JSONObject?
    private val eReceiptAdditionalFees: JSONObject?
    private val purchaseType: RspStringType?
    private val loyaltyForBanner: Boolean
    private val channel: RspStringType?
    private val submissionDate: Long?
    private val eReceiptFulfilledBy: String?
    private val eReceiptShippingStatus: String?
    private val eReceiptPOSSystem: String?
    private val eReceiptSubMerchant: String?
    private val qualifiedSurveys: List<RspSurvey>
    private val barcode: String?
    private val eReceiptMerchantEmail: String?
    private val eReceiptEmailSubject: String?
    private val eReceiptShippingCosts: Float
    private val currencyCode: String?
    private val clientMerchantName: String?
    private val loyaltyProgram: Boolean
    private val merchantSources: List<Int>
    private val paymentTerminalId: RspStringType?
    private val paymentTransactionId: RspStringType?
    private val combinedRawText: RspStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = RspStringType.opt(scanResults.receiptDate())
        receiptTime = RspStringType.opt(scanResults.receiptTime())
        retailerId = RspRetailer(scanResults.retailerId())
        products = scanResults.products()?.map { product -> RspProduct(product) } ?: emptyList()
        coupons = scanResults.coupons()?.map { coupon -> RspCoupon(coupon) } ?: emptyList()
        total = RspFloatType.opt(scanResults.total())
        tip = RspFloatType.opt(scanResults.tip())
        subtotal = RspFloatType.opt(scanResults.subtotal())
        taxes = RspFloatType.opt(scanResults.taxes())
        storeNumber = RspStringType.opt(scanResults.storeNumber())
        merchantName = RspStringType.opt(scanResults.merchantName())
        storeAddress = RspStringType.opt(scanResults.storeAddress())
        storeCity = RspStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = RspStringType.opt(scanResults.storeState())
        storeZip = RspStringType.opt(scanResults.storeZip())
        storeCountry = RspStringType.opt(scanResults.storeCountry())
        storePhone = RspStringType.opt(scanResults.storePhone())
        cashierId = RspStringType.opt(scanResults.cashierId())
        transactionId = RspStringType.opt(scanResults.transactionId())
        registerId = RspStringType.opt(scanResults.registerId())
        paymentMethods =
            scanResults.paymentMethods()?.map { paymentMethod -> RspPaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = RspStringType.opt(scanResults.taxId())
        mallName = RspStringType.opt(scanResults.mallName())
        last4cc = RspStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> RspShipment(shipment) } ?: emptyList()
        longTransactionId = RspStringType.opt(scanResults.longTransactionId())
        subtotalMatches = scanResults.subtotalMatches()
        eReceiptEmailProvider = scanResults.eReceiptEmailProvider()
        eReceiptEmailId = scanResults.eReceiptEmailId()
        eReceiptAuthenticated = scanResults.eReceiptAuthenticated()
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.eReceipt()
        eReceiptComponentEmails =
            scanResults.eReceiptComponentEmails()?.map { res -> RspReceipt(res) } ?: emptyList()
        duplicate = scanResults.duplicate()
        fraudulent = scanResults.fraudulent()
        receiptDateTime = scanResults.receiptDateTime()?.time
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds() ?: emptyList()
        merchantMatchGuess = RspStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> RspPromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> RspPromotion(promotion) } ?: emptyList()
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
        purchaseType = RspStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = RspStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> RspSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = scanResults.clientMerchantName()
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = RspStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = RspStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = RspStringType.opt(scanResults.combinedRawText())
    }

    /**
     * Converts the RSP receipt data to a JSON object.
     *
     * @return A JSONObject containing the RSP receipt data.
     */
    override fun toJS(): JSObject =
        JSObject()
            .put("receiptDate", receiptDate?.toJS())
            .put("receiptTime", receiptTime?.toJS())
            .put("retailerId", retailerId)
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
                qualifiedSurveys.map { survey -> survey.toJS() }
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
            .put("combinedRawText", combinedRawText?.toJS())

    companion object {
        /**
         * Creates an optional RSP receipt from the provided scan results.
         *
         * @param scanResults The scan results to create an RSP receipt from.
         * @return An optional RSPReceipt instance, or null if scanResults is null.
         */
        fun opt(scanResults: ScanResults?): RspReceipt? =
            if (scanResults != null) RspReceipt(scanResults) else null
    }
}
