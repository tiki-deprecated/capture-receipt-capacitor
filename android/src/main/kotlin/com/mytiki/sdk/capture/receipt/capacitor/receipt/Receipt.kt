/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.getcapacitor.JSObject
import com.microblink.core.ScanResults
import com.microblink.core.StringType
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.RspReceipt
import org.json.JSONObject

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class Receipt(scanResults: ScanResults) {

    val receiptDate: ReceiptStringType?
    val receiptTime: ReceiptStringType?
    val retailerId: ReceiptRetailer
    val products: List<ReceiptProduct>
    val coupons: List<ReceiptCoupon>
    val total: ReceiptFloatType?
    val tip: ReceiptFloatType?
    val subtotal: ReceiptFloatType?
    val taxes: ReceiptFloatType?
    val storeNumber: ReceiptStringType?
    val merchantName: ReceiptStringType?
    val storeAddress: ReceiptStringType?
    val storeCity: ReceiptStringType?
    val blinkReceiptId: String?
    val storeState: ReceiptStringType?
    val storeZip: ReceiptStringType?
    val storeCountry: ReceiptStringType?
    val storePhone: ReceiptStringType?
    val cashierId: ReceiptStringType?
    val transactionId: ReceiptStringType?
    val registerId: ReceiptStringType?
    val paymentMethods: List<ReceiptPaymentMethod>
    val taxId: ReceiptStringType?
    val mallName: ReceiptStringType?
    val last4cc: ReceiptStringType?
    val ocrConfidence: Float
    val merchantSource: String?
    val foundTopEdge: Boolean
    val foundBottomEdge: Boolean
    val eReceiptOrderNumber: String?
    val eReceiptOrderStatus: String?
    val eReceiptRawHtml: String?
    val eReceiptShippingAddress: String?
    val shipments: List<ReceiptShipment>
    val longTransactionId: ReceiptStringType?
    val subtotalMatches: Boolean
    val eReceiptEmailProvider: String?
    val eReceiptEmailId: String?
    val eReceiptAuthenticated: Boolean
    val instacartShopper: Boolean
    val eReceipt: Boolean
    val eReceiptComponentEmails: List<Receipt>
    val duplicate: Boolean
    val fraudulent: Boolean
    val receiptDateTime: Long?
    val duplicateBlinkReceiptIds: List<String>
    val merchantMatchGuess: ReceiptStringType?
    val productsPendingLookup: Int
    val qualifiedPromotions: List<ReceiptPromotion>
    val unqualifiedPromotions: List<ReceiptPromotion>
    val extendedFields: JSONObject?
    val eReceiptAdditionalFees: JSONObject?
    val purchaseType: ReceiptStringType?
    val loyaltyForBanner: Boolean
    val channel: ReceiptStringType?
    val submissionDate: Long?
    val eReceiptFulfilledBy: String?
    val eReceiptShippingStatus: String?
    val eReceiptPOSSystem: String?
    val eReceiptSubMerchant: String?
    val qualifiedSurveys: List<ReceiptSurvey>
    val barcode: String?
    val eReceiptMerchantEmail: String?
    val eReceiptEmailSubject: String?
    val eReceiptShippingCosts: Float
    val currencyCode: String?
    val clientMerchantName: ReceiptStringType?
    val loyaltyProgram: Boolean
    val merchantSources: List<Int>
    val paymentTerminalId: ReceiptStringType?
    val paymentTransactionId: ReceiptStringType?
    val combinedRawText: ReceiptStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = ReceiptStringType.opt(scanResults.receiptDate())
        receiptTime = ReceiptStringType.opt(scanResults.receiptTime())
        retailerId = ReceiptRetailer(scanResults.retailerId())
        products = scanResults.products()?.map { product -> ReceiptProduct(product) } ?: emptyList()
        coupons = scanResults.coupons()?.map { coupon -> ReceiptCoupon(coupon) } ?: emptyList()
        total = ReceiptFloatType.opt(scanResults.total())
        tip = ReceiptFloatType.opt(scanResults.tip())
        subtotal = ReceiptFloatType.opt(scanResults.subtotal())
        taxes = ReceiptFloatType.opt(scanResults.taxes())
        storeNumber = ReceiptStringType.opt(scanResults.storeNumber())
        merchantName = ReceiptStringType.opt(scanResults.merchantName())
        storeAddress = ReceiptStringType.opt(scanResults.storeAddress())
        storeCity = ReceiptStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = ReceiptStringType.opt(scanResults.storeState())
        storeZip = ReceiptStringType.opt(scanResults.storeZip())
        storeCountry = ReceiptStringType.opt(scanResults.storeCountry())
        storePhone = ReceiptStringType.opt(scanResults.storePhone())
        cashierId = ReceiptStringType.opt(scanResults.cashierId())
        transactionId = ReceiptStringType.opt(scanResults.transactionId())
        registerId = ReceiptStringType.opt(scanResults.registerId())
        paymentMethods =
            scanResults.paymentMethods()?.map { paymentMethod -> ReceiptPaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = ReceiptStringType.opt(scanResults.taxId())
        mallName = ReceiptStringType.opt(scanResults.mallName())
        last4cc = ReceiptStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> ReceiptShipment(shipment) } ?: emptyList()
        longTransactionId = ReceiptStringType.opt(scanResults.longTransactionId())
        subtotalMatches = scanResults.subtotalMatches()
        eReceiptEmailProvider = scanResults.eReceiptEmailProvider()
        eReceiptEmailId = scanResults.eReceiptEmailId()
        eReceiptAuthenticated = scanResults.eReceiptAuthenticated()
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.eReceipt()
        eReceiptComponentEmails =
            scanResults.eReceiptComponentEmails()?.map { res -> Receipt(res) }
                ?: emptyList()
        duplicate = scanResults.duplicate()
        fraudulent = scanResults.fraudulent()
        receiptDateTime = scanResults.receiptDateTime()?.time
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds() ?: emptyList()
        merchantMatchGuess = ReceiptStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> ReceiptPromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> ReceiptPromotion(promotion) } ?: emptyList()
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
        purchaseType = ReceiptStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = ReceiptStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> ReceiptSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = ReceiptStringType.opt(StringType(scanResults.clientMerchantName()))
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = ReceiptStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = ReceiptStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = ReceiptStringType.opt(scanResults.combinedRawText())
    }

    fun toRsp(requestId: String) : JSObject {
        return RspReceipt(requestId, this).toJS()
    }

    companion object {
        /**
         * Creates an optional RSP receipt from the provided scan results.
         *
         * @param scanResults The scan results to create an RSP receipt from.
         * @return An optional RSPReceipt instance, or null if scanResults is null.
         */
        fun opt(scanResults: ScanResults?): Receipt? =
            if (scanResults != null) Receipt(scanResults) else null
    }
}
