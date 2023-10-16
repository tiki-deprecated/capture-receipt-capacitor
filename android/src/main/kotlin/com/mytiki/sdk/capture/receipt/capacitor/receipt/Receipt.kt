/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.getcapacitor.JSObject
import com.microblink.core.ScanResults
import com.microblink.core.StringType
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.RspReceipt
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelCoupon
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelFloatType
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelPaymentMethod
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelProduct
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelPromotion
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelRetailer
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelShipment
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelStringType
import com.mytiki.sdk.capture.receipt.capacitor.receipt.model.ModelSurvey
import org.json.JSONObject

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class Receipt(scanResults: ScanResults) {

    val receiptDate: ModelStringType?
    val receiptTime: ModelStringType?
    val retailerId: ModelRetailer
    val products: List<ModelProduct>
    val coupons: List<ModelCoupon>
    val total: ModelFloatType?
    val tip: ModelFloatType?
    val subtotal: ModelFloatType?
    val taxes: ModelFloatType?
    val storeNumber: ModelStringType?
    val merchantName: ModelStringType?
    val storeAddress: ModelStringType?
    val storeCity: ModelStringType?
    val blinkReceiptId: String?
    val storeState: ModelStringType?
    val storeZip: ModelStringType?
    val storeCountry: ModelStringType?
    val storePhone: ModelStringType?
    val cashierId: ModelStringType?
    val transactionId: ModelStringType?
    val registerId: ModelStringType?
    val paymentMethods: List<ModelPaymentMethod>
    val taxId: ModelStringType?
    val mallName: ModelStringType?
    val last4cc: ModelStringType?
    val ocrConfidence: Float
    val merchantSource: String?
    val foundTopEdge: Boolean
    val foundBottomEdge: Boolean
    val eReceiptOrderNumber: String?
    val eReceiptOrderStatus: String?
    val eReceiptRawHtml: String?
    val eReceiptShippingAddress: String?
    val shipments: List<ModelShipment>
    val longTransactionId: ModelStringType?
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
    val merchantMatchGuess: ModelStringType?
    val productsPendingLookup: Int
    val qualifiedPromotions: List<ModelPromotion>
    val unqualifiedPromotions: List<ModelPromotion>
    val extendedFields: JSONObject?
    val eReceiptAdditionalFees: JSONObject?
    val purchaseType: ModelStringType?
    val loyaltyForBanner: Boolean
    val channel: ModelStringType?
    val submissionDate: Long?
    val eReceiptFulfilledBy: String?
    val eReceiptShippingStatus: String?
    val eReceiptPOSSystem: String?
    val eReceiptSubMerchant: String?
    val qualifiedSurveys: List<ModelSurvey>
    val barcode: String?
    val eReceiptMerchantEmail: String?
    val eReceiptEmailSubject: String?
    val eReceiptShippingCosts: Float
    val currencyCode: String?
    val clientMerchantName: ModelStringType?
    val loyaltyProgram: Boolean
    val merchantSources: List<Int>
    val paymentTerminalId: ModelStringType?
    val paymentTransactionId: ModelStringType?
    val combinedRawText: ModelStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = ModelStringType.opt(scanResults.receiptDate())
        receiptTime = ModelStringType.opt(scanResults.receiptTime())
        retailerId = ModelRetailer(scanResults.retailerId())
        products = scanResults.products()?.map { product -> ModelProduct(product) } ?: emptyList()
        coupons = scanResults.coupons()?.map { coupon -> ModelCoupon(coupon) } ?: emptyList()
        total = ModelFloatType.opt(scanResults.total())
        tip = ModelFloatType.opt(scanResults.tip())
        subtotal = ModelFloatType.opt(scanResults.subtotal())
        taxes = ModelFloatType.opt(scanResults.taxes())
        storeNumber = ModelStringType.opt(scanResults.storeNumber())
        merchantName = ModelStringType.opt(scanResults.merchantName())
        storeAddress = ModelStringType.opt(scanResults.storeAddress())
        storeCity = ModelStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = ModelStringType.opt(scanResults.storeState())
        storeZip = ModelStringType.opt(scanResults.storeZip())
        storeCountry = ModelStringType.opt(scanResults.storeCountry())
        storePhone = ModelStringType.opt(scanResults.storePhone())
        cashierId = ModelStringType.opt(scanResults.cashierId())
        transactionId = ModelStringType.opt(scanResults.transactionId())
        registerId = ModelStringType.opt(scanResults.registerId())
        paymentMethods =
            scanResults.paymentMethods()?.map { paymentMethod -> ModelPaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = ModelStringType.opt(scanResults.taxId())
        mallName = ModelStringType.opt(scanResults.mallName())
        last4cc = ModelStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> ModelShipment(shipment) } ?: emptyList()
        longTransactionId = ModelStringType.opt(scanResults.longTransactionId())
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
        merchantMatchGuess = ModelStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> ModelPromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> ModelPromotion(promotion) } ?: emptyList()
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
        purchaseType = ModelStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = ModelStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> ModelSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = ModelStringType.opt(StringType(scanResults.clientMerchantName()))
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = ModelStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = ModelStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = ModelStringType.opt(scanResults.combinedRawText())
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
