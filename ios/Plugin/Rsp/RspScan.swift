/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspScan : Rsp {
    private let receiptDate: RspStringType?
    private let receiptTime: RspStringType?
    private let retailerId: RspRetailer
    private let products: List<RspProduct>
    private let coupons: List<RspCoupon>
    private let total: RspFloatType?
    private let tip: RspFloatType?
    private let subtotal: RspFloatType?
    private let taxes: RspFloatType?
    private let storeNumber: RspStringType?
    private let merchantName: RspStringType?
    private let storeAddress: RspStringType?
    private let storeCity: RspStringType?
    private let blinkReceiptId: String?
    private let storeState: RspStringType?
    private let storeZip: RspStringType?
    private let storeCountry: RspStringType?
    private let storePhone: RspStringType?
    private let cashierId: RspStringType?
    private let transactionId: RspStringType?
    private let registerId: RspStringType?
    private let paymentMethods: List<RspPaymentMethod>
    private let taxId: RspStringType?
    private let mallName: RspStringType?
    private let last4cc: RspStringType?
    private let ocrConfidence: Float
    private let merchantSource: String?
    private let foundTopEdge: Boolean
    private let foundBottomEdge: Boolean
    private let eReceiptOrderNumber: String?
    private let eReceiptOrderStatus: String?
    private let eReceiptRawHtml: String?
    private let eReceiptShippingAddress: String?
    private let shipments: List<RspShipment>
    private let longTransactionId: RspStringType?
    private let subtotalMatches: Boolean
    private let eReceiptEmailProvider: String?
    private let eReceiptEmailId: String?
    private let eReceiptAuthenticated: Boolean
    private let instacartShopper: Boolean
    private let eReceipt: Boolean
    private let eReceiptComponentEmails: List<RspScan>
    private let duplicate: Boolean
    private let fraudulent: Boolean
    private let receiptDateTime: Int64?
    private let duplicateBlinkReceiptIds: List<String>
    private let merchantMatchGuess: RspStringType?
    private let productsPendingLookup: Int
    private let qualifiedPromotions: List<RspPromotion>
    private let unqualifiedPromotions: List<RspPromotion>
    private let extendedFields: JSObject?
    private let eReceiptAdditionalFees: JSObject?
    private let purchaseType: RspStringType?
    private let loyaltyForBanner: Boolean
    private let channel: RspStringType?
    private let submissionDate: Int64?
    private let eReceiptFulfilledBy: String?
    private let eReceiptShippingStatus: String?
    private let eReceiptPOSSystem: String?
    private let eReceiptSubMerchant: String?
    private let qualifiedSurveys: List<RspSurvey>
    private let barcode: String?
    private let eReceiptMerchantEmail: String?
    private let eReceiptEmailSubject: String?
    private let eReceiptShippingCosts: Float
    private let currencyCode: String?
    private let clientMerchantName: String?
    private let loyaltyProgram: Boolean
    private let merchantSources: List<Int>
    private let paymentTerminalId: RspStringType?
    private let paymentTransactionId: RspStringType?
    private let combinedRawText: RspStringType?
    
    init(scanResults: ScanResults) {
        receiptDate = RspStringType.opt(scanResults.receiptDate())
        receiptTime = RspStringType.opt(scanResults.receiptTime())
        retailerId = RspRetailer(scanResults.retailerId())
        products = scanResults.products()?.map { product in RspProduct(product) } ?? []
        coupons = scanResults.coupons()?.map { coupon in RspCoupon(coupon) } ?? []
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
        paymentMethods = scanResults.paymentMethods()?.map { paymentMethod in RspPaymentMethod(paymentMethod) } ?? []
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
        shipments = scanResults.shipments()?.map { shipment in RspShipment(shipment) } ?? []
        longTransactionId = RspStringType.opt(scanResults.longTransactionId())
        subtotalMatches = scanResults.subtotalMatches()
        eReceiptEmailProvider = scanResults.eReceiptEmailProvider()
        eReceiptEmailId = scanResults.eReceiptEmailId()
        eReceiptAuthenticated = scanResults.eReceiptAuthenticated()
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.eReceipt()
        eReceiptComponentEmails = scanResults.eReceiptComponentEmails()?.map { res in RspScan(res) } ?? []
        duplicate = scanResults.duplicate()
        fraudulent = scanResults.fraudulent()
        receiptDateTime = scanResults.receiptDateTime()?.time
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds() ?? []
        merchantMatchGuess = RspStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions = scanResults.qualified()?.map { promotion in RspPromotion(promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualified()?.map { promotion in RspPromotion(promotion) } ?? []
        if let extendedFields = scanResults.extendedFields() {
            var extendedFieldsDictionary = [String: Any]()
            for entry in extendedFields {
                extendedFieldsDictionary[entry.key] = entry.value
            }
            self.extendedFields = extendedFieldsDictionary
        } else {
            self.extendedFields = nil
        }
        if let eReceiptAdditionalFees = scanResults.eReceiptAdditionalFees() {
            var additionalFeesDictionary = [String: Any]()
            for entry in eReceiptAdditionalFees {
                additionalFeesDictionary[entry.key] = entry.value
            }
            self.eReceiptAdditionalFees = additionalFeesDictionary
        } else {
            self.eReceiptAdditionalFees = nil
        }
        purchaseType = RspStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = RspStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys = scanResults.qualifiedSurveys()?.map { survey in RspSurvey(survey) } ?? []
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = scanResults.clientMerchantName()
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?? []
        paymentTerminalId = RspStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = RspStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = RspStringType.opt(scanResults.combinedRawText())
    }

    func toJson() -> JSObject {
        JSObject.updateValue("receiptDate", receiptDate?.toJson())
        JSObject.updateValue("receiptTime", receiptTime?.toJson())
        JSObject.updateValue("retailerId", retailerId.toJson())
        JSObject.updateValue("products", JSONArray(products.map { prd -> prd.toJson() }))
        JSObject.updateValue("coupons", JSONArray(coupons.map { coupon -> coupon.toJson() }))
        JSObject.updateValue("total", total?.toJson())
        JSObject.updateValue("tip", tip?.toJson())
        JSObject.updateValue("subtotal", subtotal?.toJson())
        JSObject.updateValue("taxes", taxes?.toJson())
        JSObject.updateValue("storeNumber", storeNumber?.toJson())
        JSObject.updateValue("merchantName", merchantName?.toJson())
        JSObject.updateValue("storeAddress", storeAddress?.toJson())
        JSObject.updateValue("storeCity", storeCity?.toJson())
        JSObject.updateValue("blinkReceiptId", blinkReceiptId)
        JSObject.updateValue("storeState", storeState?.toJson())
        JSObject.updateValue("storeZip", storeZip?.toJson())
        JSObject.updateValue("storeCountry", storeCountry?.toJson())
        JSObject.updateValue("storePhone", storePhone?.toJson())
        JSObject.updateValue("cashierId", cashierId?.toJson())
        JSObject.updateValue("transactionId", transactionId?.toJson())
        JSObject.updateValue("registerId", registerId?.toJson())
        JSObject.updateValue("paymentMethods", JSONArray(paymentMethods.map { method -> method.toJson() }))
        JSObject.updateValue("taxId", taxId?.toJson())
        JSObject.updateValue("mallName", mallName?.toJson())
        JSObject.updateValue("last4cc", last4cc?.toJson())
        JSObject.updateValue("ocrConfidence", ocrConfidence)
        JSObject.updateValue("merchantSource", merchantSource)
        JSObject.updateValue("foundTopEdge", foundTopEdge)
        JSObject.updateValue("foundBottomEdge", foundBottomEdge)
        JSObject.updateValue("eReceiptOrderNumber", eReceiptOrderNumber)
        JSObject.updateValue("eReceiptOrderStatus", eReceiptOrderStatus)
        JSObject.updateValue("eReceiptRawHtml", eReceiptRawHtml)
        JSObject.updateValue("eReceiptShippingAddress", eReceiptShippingAddress)
        JSObject.updateValue("shipments", JSONArray(shipments.map { shipment -> shipment.toJson() }))
        JSObject.updateValue("longTransactionId", longTransactionId?.toJson())
        JSObject.updateValue("subtotalMatches", subtotalMatches)
        JSObject.updateValue("eReceiptEmailProvider", eReceiptEmailProvider)
        JSObject.updateValue("eReceiptEmailId", eReceiptEmailId)
        JSObject.updateValue("eReceiptAuthenticated", eReceiptAuthenticated)
        JSObject.updateValue("instacartShopper", instacartShopper)
        JSObject.updateValue("eReceipt", eReceipt)
        JSObject.updateValue(
            "eReceiptComponentEmails",
            JSONArray(eReceiptComponentEmails.map { email -> email.toJson() })
        )
        JSObject.updateValue("duplicate", duplicate)
        JSObject.updateValue("fraudulent", fraudulent)
        JSObject.updateValue("receiptDateTime", receiptDateTime)
        JSObject.updateValue("duplicateBlinkReceiptIds", JSONArray(duplicateBlinkReceiptIds))
        JSObject.updateValue("merchantMatchGuess", merchantMatchGuess?.toJson())
        JSObject.updateValue("productsPendingLookup", productsPendingLookup)
        JSObject.updateValue(
            "qualifiedPromotions",
            JSONArray(qualifiedPromotions.map { promo -> promo.toJson() })
        )
        JSObject.updateValue(
            "unqualifiedPromotions",
            JSONArray(unqualifiedPromotions.map { promo -> promo.toJson() })
        )
        JSObject.updateValue("extendedFields", extendedFields)
        JSObject.updateValue("eReceiptAdditionalFees", eReceiptAdditionalFees)
        JSObject.updateValue("purchaseType", purchaseType?.toJson())
        JSObject.updateValue("loyaltyForBanner", loyaltyForBanner)
        JSObject.updateValue("channel", channel?.toJson())
        JSObject.updateValue("submissionDate", submissionDate)
        JSObject.updateValue("eReceiptFulfilledBy", eReceiptFulfilledBy)
        JSObject.updateValue("eReceiptShippingStatus", eReceiptShippingStatus)
        JSObject.updateValue("eReceiptPOSSystem", eReceiptPOSSystem)
        JSObject.updateValue("eReceiptSubMerchant", eReceiptSubMerchant)
        JSObject.updateValue(
            "qualifiedSurveys",
            JSONArray(qualifiedSurveys.map { survey -> survey.toJson() })
        )
        JSObject.updateValue("barcode", barcode)
        JSObject.updateValue("eReceiptMerchantEmail", eReceiptMerchantEmail)
        JSObject.updateValue("eReceiptEmailSubject", eReceiptEmailSubject)
        JSObject.updateValue("eReceiptShippingCosts", eReceiptShippingCosts)
        JSObject.updateValue("currencyCode", currencyCode)
        JSObject.updateValue("clientMerchantName", clientMerchantName)
        JSObject.updateValue("loyaltyProgram", loyaltyProgram)
        JSObject.updateValue("merchantSources", JSONArray(merchantSources))
        JSObject.updateValue("paymentTerminalId", paymentTerminalId?.toJson())
        JSObject.updateValue("paymentTransactionId", paymentTransactionId?.toJson())
        JSObject.updateValue("combinedRawText", combinedRawText?.toJson())
    }
}
