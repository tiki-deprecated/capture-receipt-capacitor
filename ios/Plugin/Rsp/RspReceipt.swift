/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspReceipt : Rsp {
    private let receiptDate: RspStringType?
    private let receiptTime: RspStringType?
    private let retailerId: RspRetailer
    private let products: [RspProduct]
    private let coupons: [RspCoupon]
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
    private let paymentMethods: [RspPaymentMethod]
    private let taxId: RspStringType?
    private let mallName: RspStringType?
    private let last4cc: RspStringType?
    private let ocrConfidence: Float
    private let merchantSource: String?
    private let foundTopEdge: Bool
    private let foundBottomEdge: Bool
    private let eReceiptOrderNumber: String?
    private let eReceiptOrderStatus: String?
    private let eReceiptRawHtml: String?
    private let eReceiptShippingAddress: String?
    private let shipments: [RspShipment]
    private let longTransactionId: RspStringType?
    private let subtotalMatches: Bool
    private let eReceiptEmailProvider: String?
    private let eReceiptEmailId: String?
    private let eReceiptAuthenticated: Bool
    private let instacartShopper: Bool
    private let eReceipt: Bool
    private let eReceiptComponentEmails: [RspReceipt]
    private let duplicate: Bool
    private let fraudulent: Bool
    private let receiptDateTime: Int64?
    private let duplicateBlinkReceiptIds: [String]
    private let merchantMatchGuess: RspStringType?
    private let productsPendingLookup: Int
    private let qualifiedPromotions: [RspPromotion]
    private let unqualifiedPromotions: [RspPromotion]
    private let extendedFields: JSObject?
    private let eReceiptAdditionalFees: JSObject?
    private let purchaseType: RspStringType?
    private let loyaltyForBanner: Bool
    private let channel: RspStringType?
    private let submissionDate: Int64?
    private let eReceiptFulfilledBy: String?
    private let eReceiptShippingStatus: String?
    private let eReceiptPOSSystem: String?
    private let eReceiptSubMerchant: String?
    private let qualifiedSurveys: [RspSurvey]
    private let barcode: String?
    private let eReceiptMerchantEmail: String?
    private let eReceiptEmailSubject: String?
    private let eReceiptShippingCosts: Float
    private let currencyCode: String?
    private let clientMerchantName: String?
    private let loyaltyProgram: Bool
    private let merchantSources: [Int]
    private let paymentTerminalId: RspStringType?
    private let paymentTransactionId: RspStringType?
    private let combinedRawText: RspStringType?
    
    init(scanResults: BRScanResults) {
        receiptDate = RspStringType(stringType: scanResults.receiptDate)
        receiptTime = RspStringType(stringType: scanResults.receiptTime)
        retailerId = RspRetailer(retailer: scanResults.retailerId)
        products = scanResults.products.map { product in RspProduct(product) } ?? []
        coupons = scanResults.coupons.map { coupon in RspCoupon(coupon: coupon) } 
        total = RspFloatType(floatType: scanResults.total)
        tip = RspFloatType(floatType: scanResults.tip)
        subtotal = RspFloatType(floatType: scanResults.subtotal)
        taxes = RspFloatType(floatType: scanResults.taxes)
        storeNumber = RspStringType(stringType: scanResults.storeNumber)
        merchantName = RspStringType(stringType: scanResults.merchantName)
        storeAddress = RspStringType(stringType: scanResults.storeAddress)
        storeCity = RspStringType(stringType: scanResults.storeCity)
        blinkReceiptId = scanResults.blinkReceiptId
        storeState = RspStringType(stringType: scanResults.storeState)
        storeZip = RspStringType(stringType: scanResults.storeZip)
//        storeCountry = RspStringType(stringType: scanResults.store)
        storePhone = RspStringType(stringType: scanResults.storePhone)
        cashierId = RspStringType(stringType: scanResults.cashierId)
        transactionId = RspStringType(stringType: scanResults.transactionId)
        registerId = RspStringType(stringType: scanResults.registerId)
        paymentMethods = scanResults.paymentMethods.map { paymentMethod in RspPaymentMethod(paymentMethod) } ?? []
        taxId = RspStringType(stringType: scanResults.taxId)
        mallName = RspStringType(stringType: scanResults.mallName)
        last4cc = RspStringType(stringType: scanResults.last4CC)
        ocrConfidence = scanResults.ocrConfidence
        merchantSource = scanResults.merchantSources
        foundTopEdge = scanResults.foundTopEdge
        foundBottomEdge = scanResults.foundBottomEdge
        eReceiptOrderNumber = scanResults.ereceiptOrderNumber
        eReceiptOrderStatus = scanResults.ereceiptOrderStatus
        eReceiptRawHtml = scanResults.ereceiptRawHTML
//        eReceiptShippingAddress = scanResults.ereceiptShi ReceiptShippingAddress
        shipments = scanResults.shipments.map { shipment in RspShipment(shipment) } ?? []
        longTransactionId = RspStringType(stringType: scanResults.longTransactionId)
        subtotalMatches = scanResults.subtotalMatches
        eReceiptEmailProvider = scanResults.ereceiptEmailProvider
        eReceiptEmailId = scanResults.ereceiptEmailId
        eReceiptAuthenticated = scanResults.ereceiptAuthenticated
        instacartShopper = scanResults.isInstacartShopper
//        eReceipt = scanResults.ereceipt
        eReceiptComponentEmails = scanResults.ereceiptComponentEmails.map { res in RspScan(res) } ?? []
        duplicate = scanResults.isDuplicate
        fraudulent = scanResults.isFraudulent
//        receiptDateTime = scanResults.receiptTime
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds ?? []
        merchantMatchGuess = RspStringType(stringType: scanResults.merchantGuess)
        productsPendingLookup = scanResults.productsPendingLookup
        qualifiedPromotions = scanResults.qualifiedPromotions.map { promotion in RspPromotion(promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualifiedPromotions.map { promotion in RspPromotion(promotion) } ?? []
        if let extendedFields = scanResults.extendedFields {
            var extendedFieldsDictionary = [String: Any]()
            for entry in extendedFields {
                extendedFieldsDictionary[entry.key as! String] = entry.value
            }
            self.extendedFields = extendedFieldsDictionary
        } else {
            self.extendedFields = nil
        }
        if let eReceiptAdditionalFees = scanResults.ereceiptAdditionalFees {
            var additionalFeesDictionary = [String: Any]()
            for entry in eReceiptAdditionalFees {
                additionalFeesDictionary[entry.key] = entry.value
            }
            self.eReceiptAdditionalFees = additionalFeesDictionary
        } else {
            self.eReceiptAdditionalFees = nil
        }
        purchaseType = RspStringType(stringType: scanResults.purchaseType)
//        loyaltyForBanner = scanResults.loyaltyForBanner
        channel = RspStringType(stringType: scanResults.channel)
//        submissionDate = scanResults.submissionDate.time
        eReceiptFulfilledBy = scanResults.ereceiptFulfilledBy
//        eReceiptShippingStatus = scanResults.eReceiptShippingStatus
        eReceiptPOSSystem = scanResults.ereceiptPOSSystem
        eReceiptSubMerchant = scanResults.ereceiptSubMerchant
        qualifiedSurveys = scanResults.qualifiedSurveys.map { survey in RspSurvey(survey) } ?? []
        barcode = scanResults.barcode
        eReceiptMerchantEmail = scanResults.ereceiptMerchantEmail
        eReceiptEmailSubject = scanResults.ereceiptEmailSubject
        eReceiptShippingCosts = scanResults.ereceiptShippingCosts
        currencyCode = scanResults.currencyCode
        clientMerchantName = scanResults.clientMerchantName
        loyaltyProgram = scanResults.loyaltyProgram
        merchantSources = scanResults.merchantSources ?? []
        paymentTerminalId = RspStringType(stringType: scanResults.paymentTerminalId)
        paymentTransactionId = RspStringType(stringType: scanResults.paymentTransactionId)
        combinedRawText = RspStringType(stringType: scanResults.combinedRawText)
    }

    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "receiptDate" : receiptDate,
            "receiptTime" : receiptTime,
            "retailerId" : retailerId,
            "products": JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() })
        ]
        JSObject.updateValue("products", JSONArray(products.map { prd -> prd.toJson() }))
        JSObject.updateValue("coupons", JSONArray(coupons.map { coupon -> coupon.toJson() }))
        JSObject.updateValue("total", total?.toJson)
        JSObject.updateValue("tip", tip?.toJson)
        JSObject.updateValue("subtotal", subtotal?.toJson)
        JSObject.updateValue("taxes", taxes?.toJson)
        JSObject.updateValue("storeNumber", storeNumber?.toJson)
        JSObject.updateValue("merchantName", merchantName?.toJson)
        JSObject.updateValue("storeAddress", storeAddress?.toJson)
        JSObject.updateValue("storeCity", storeCity?.toJson)
        JSObject.updateValue("blinkReceiptId", blinkReceiptId)
        JSObject.updateValue("storeState", storeState?.toJson)
        JSObject.updateValue("storeZip", storeZip?.toJson)
        JSObject.updateValue("storeCountry", storeCountry?.toJson)
        JSObject.updateValue("storePhone", storePhone?.toJson)
        JSObject.updateValue("cashierId", cashierId?.toJson)
        JSObject.updateValue("transactionId", transactionId?.toJson)
        JSObject.updateValue("registerId", registerId?.toJson)
        JSObject.updateValue("paymentMethods", JSONArray(paymentMethods.map { method -> method.toJson() }))
        JSObject.updateValue("taxId", taxId?.toJson)
        JSObject.updateValue("mallName", mallName?.toJson)
        JSObject.updateValue("last4cc", last4cc?.toJson)
        JSObject.updateValue("ocrConfidence", ocrConfidence)
        JSObject.updateValue("merchantSource", merchantSource)
        JSObject.updateValue("foundTopEdge", foundTopEdge)
        JSObject.updateValue("foundBottomEdge", foundBottomEdge)
        JSObject.updateValue("eReceiptOrderNumber", eReceiptOrderNumber)
        JSObject.updateValue("eReceiptOrderStatus", eReceiptOrderStatus)
        JSObject.updateValue("eReceiptRawHtml", eReceiptRawHtml)
        JSObject.updateValue("eReceiptShippingAddress", eReceiptShippingAddress)
        JSObject.updateValue("shipments", JSONArray(shipments.map { shipment -> shipment.toJson() }))
        JSObject.updateValue("longTransactionId", longTransactionId?.toJson)
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
        JSObject.updateValue("merchantMatchGuess", merchantMatchGuess?.toJson)
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
        JSObject.updateValue("purchaseType", purchaseType?.toJson)
        JSObject.updateValue("loyaltyForBanner", loyaltyForBanner)
        JSObject.updateValue("channel", channel?.toJson)
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
        JSObject.updateValue("paymentTerminalId", paymentTerminalId?.toJson)
        JSObject.updateValue("paymentTransactionId", paymentTransactionId?.toJson)
        JSObject.updateValue("combinedRawText", combinedRawText?.toJson)
    }
}
