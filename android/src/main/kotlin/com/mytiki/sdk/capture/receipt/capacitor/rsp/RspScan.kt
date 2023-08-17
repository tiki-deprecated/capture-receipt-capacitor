/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import org.json.JSONArray
import org.json.JSONObject

class RspScan(scanResults: ScanResults) : Rsp {
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
    private val eReceiptComponentEmails: List<RspScan>
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
            scanResults.eReceiptComponentEmails()?.map { res -> RspScan(res) } ?: emptyList()
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
            val extendedFields = JSONObject()
            scanResults.extendedFields()
                ?.forEach { entry -> extendedFields.put(entry.key, entry.value) }
            extendedFields
        } else null
        eReceiptAdditionalFees = if (scanResults.eReceiptAdditionalFees() != null) {
            val additionalFees = JSONObject()
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

    override fun toJson(): JSONObject =
        JSONObject()
            .put("receiptDate", receiptDate?.toJson())
            .put("receiptTime", receiptTime?.toJson())
            .put("retailerId", retailerId.toJson())
            .put("products", JSONArray(products.map { prd -> prd.toJson() }))
            .put("coupons", JSONArray(coupons.map { coupon -> coupon.toJson() }))
            .put("total", total?.toJson())
            .put("tip", tip?.toJson())
            .put("subtotal", subtotal?.toJson())
            .put("taxes", taxes?.toJson())
            .put("storeNumber", storeNumber?.toJson())
            .put("merchantName", merchantName?.toJson())
            .put("storeAddress", storeAddress?.toJson())
            .put("storeCity", storeCity?.toJson())
            .put("blinkReceiptId", blinkReceiptId)
            .put("storeState", storeState?.toJson())
            .put("storeZip", storeZip?.toJson())
            .put("storeCountry", storeCountry?.toJson())
            .put("storePhone", storePhone?.toJson())
            .put("cashierId", cashierId?.toJson())
            .put("transactionId", transactionId?.toJson())
            .put("registerId", registerId?.toJson())
            .put("paymentMethods", JSONArray(paymentMethods.map { method -> method.toJson() }))
            .put("taxId", taxId?.toJson())
            .put("mallName", mallName?.toJson())
            .put("last4cc", last4cc?.toJson())
            .put("ocrConfidence", ocrConfidence)
            .put("merchantSource", merchantSource)
            .put("foundTopEdge", foundTopEdge)
            .put("foundBottomEdge", foundBottomEdge)
            .put("eReceiptOrderNumber", eReceiptOrderNumber)
            .put("eReceiptOrderStatus", eReceiptOrderStatus)
            .put("eReceiptRawHtml", eReceiptRawHtml)
            .put("eReceiptShippingAddress", eReceiptShippingAddress)
            .put("shipments", JSONArray(shipments.map { shipment -> shipment.toJson() }))
            .put("longTransactionId", longTransactionId?.toJson())
            .put("subtotalMatches", subtotalMatches)
            .put("eReceiptEmailProvider", eReceiptEmailProvider)
            .put("eReceiptEmailId", eReceiptEmailId)
            .put("eReceiptAuthenticated", eReceiptAuthenticated)
            .put("instacartShopper", instacartShopper)
            .put("eReceipt", eReceipt)
            .put(
                "eReceiptComponentEmails",
                JSONArray(eReceiptComponentEmails.map { email -> email.toJson() })
            )
            .put("duplicate", duplicate)
            .put("fraudulent", fraudulent)
            .put("receiptDateTime", receiptDateTime)
            .put("duplicateBlinkReceiptIds", duplicateBlinkReceiptIds)
            .put("merchantMatchGuess", merchantMatchGuess?.toJson())
            .put("productsPendingLookup", productsPendingLookup)
            .put(
                "qualifiedPromotions",
                JSONArray(qualifiedPromotions.map { promo -> promo.toJson() })
            )
            .put(
                "unqualifiedPromotions",
                JSONArray(unqualifiedPromotions.map { promo -> promo.toJson() })
            )
            .put("extendedFields", extendedFields)
            .put("eReceiptAdditionalFees", eReceiptAdditionalFees)
            .put("purchaseType", purchaseType?.toJson())
            .put("loyaltyForBanner", loyaltyForBanner)
            .put("channel", channel?.toJson())
            .put("submissionDate", submissionDate)
            .put("eReceiptFulfilledBy", eReceiptFulfilledBy)
            .put("eReceiptShippingStatus", eReceiptShippingStatus)
            .put("eReceiptPOSSystem", eReceiptPOSSystem)
            .put("eReceiptSubMerchant", eReceiptSubMerchant)
            .put(
                "qualifiedSurveys",
                JSONArray(qualifiedSurveys.map { survey -> survey.toJson() })
            )
            .put("barcode", barcode)
            .put("eReceiptMerchantEmail", eReceiptMerchantEmail)
            .put("eReceiptEmailSubject", eReceiptEmailSubject)
            .put("eReceiptShippingCosts", eReceiptShippingCosts)
            .put("currencyCode", currencyCode)
            .put("clientMerchantName", clientMerchantName)
            .put("loyaltyProgram", loyaltyProgram)
            .put("merchantSources", JSONArray(merchantSources))
            .put("paymentTerminalId", paymentTerminalId?.toJson())
            .put("paymentTransactionId", paymentTransactionId?.toJson())
            .put("combinedRawText", combinedRawText?.toJson())

    companion object {
        fun opt(scanResults: ScanResults?): RspScan? =
            if (scanResults != null) RspScan(scanResults) else null
    }
}