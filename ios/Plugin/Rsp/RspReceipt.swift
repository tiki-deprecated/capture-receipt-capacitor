/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspReceipt : Rsp {
    private let receiptDate: String?
    private let receiptTime: String?
    private let retailerId:  UInt
    private let products: [RspProduct]
    private let coupons: [RspCoupon]
    private let total: Float?
    private let tip: Float?
    private let subtotal: Float?
    private let taxes: Float?
    private let storeNumber: String?
    private let merchantName: String?
    private let storeAddress: String?
    private let storeCity: String?
    private let blinkReceiptId: String?
    private let storeState: String?
    private let storeZip: String?
    private let storePhone: String?
    private let cashierId: String?
    private let transactionId: String?
    private let registerId: String?
    private let paymentMethods: [RspPaymentMethod]
    private let taxId: String?
    private let mallName: String?
    private let last4cc: String?
    private let ocrConfidence: Float
    private let foundTopEdge: Bool
    private let foundBottomEdge: Bool
    private let eReceiptOrderNumber: String?
    private let eReceiptOrderStatus: String?
    private let eReceiptRawHtml: String?
    private let shipments: [RspShipment]
    private let longTransactionId: String?
    private let subtotalMatches: Bool
    private let eReceiptEmailProvider: String?
    private let eReceiptEmailId: String?
    private let eReceiptAuthenticated: Bool
    private let instacartShopper: Bool
    private let eReceiptComponentEmails: [RspReceipt]
    private let duplicate: Bool
    private let fraudulent: Bool
    private let duplicateBlinkReceiptIds: [String]
    private let merchantMatchGuess: String?
    private let productsPendingLookup: Int
    private let qualifiedPromotions: [RspPromotion]
    private let unqualifiedPromotions: [RspPromotion]
    private let extendedFields: JSObject?
    private let eReceiptAdditionalFees: JSObject?
    private let purchaseType: String?
    private let channel: String?
    private let eReceiptFulfilledBy: String?
    private let eReceiptPOSSystem: String?
    private let eReceiptSubMerchant: String?
    private let barcode: String?
    private let eReceiptMerchantEmail: String?
    private let eReceiptEmailSubject: String?
    private let eReceiptShippingCosts: Float
    private let currencyCode: String?
    private let clientMerchantName: String?
    private let loyaltyProgram: Bool
    private let merchantSources: [NSNumber]
    private let paymentTerminalId: String?
    private let paymentTransactionId: String?
    private let combinedRawText: String?
    //    private let storeCountry: String?
    //    private let eReceiptShippingAddress: String?
    //    private let eReceipt: Bool
    //    private let receiptDateTime: Int64?
    //    private let submissionDate: Int64?
    //    private let eReceiptShippingStatus: String?
    //    private let qualifiedSurveys: [RspSurvey]
//    private let merchantSource: NSNumber?

    
    init(scanResults: BRScanResults) {
        receiptDate = scanResults.receiptDate.value
        receiptTime = scanResults.receiptTime.value
        retailerId = scanResults.retailerId.rawValue
        products = scanResults.products.map { product in RspProduct(product: product) } ?? []
        coupons = scanResults.coupons.map { coupon in RspCoupon(coupon: coupon) } 
        total = scanResults.total.value
        tip = scanResults.tip.value
        subtotal = scanResults.subtotal.value
        taxes = scanResults.taxes.value
        storeNumber = scanResults.storeNumber.value
        merchantName = scanResults.merchantName.value
        storeAddress = scanResults.storeAddress.value
        storeCity = scanResults.storeCity.value
        blinkReceiptId = scanResults.blinkReceiptId
        storeState = scanResults.storeState.value
        storeZip = scanResults.storeZip.value
        storePhone = scanResults.storePhone.value
        cashierId = scanResults.cashierId.value
        transactionId = scanResults.transactionId.value
        registerId = scanResults.registerId.value
        paymentMethods = scanResults.paymentMethods.map { payment in RspPaymentMethod(paymentMethod: payment.method.value, cardType: payment.cardType.value, cardIssuer: payment.cardIssuer.value, amount: payment.amount.value) } ?? []
        taxId = scanResults.taxId.value
        mallName = scanResults.mallName.value
        last4cc = scanResults.last4CC.value
        ocrConfidence = scanResults.ocrConfidence
        merchantSources = scanResults.merchantSources
        foundTopEdge = scanResults.foundTopEdge
        foundBottomEdge = scanResults.foundBottomEdge
        eReceiptOrderNumber = scanResults.ereceiptOrderNumber
        eReceiptOrderStatus = scanResults.ereceiptOrderStatus
        eReceiptRawHtml = scanResults.ereceiptRawHTML
        shipments = scanResults.shipments.map { shipment in RspShipment(shipment: shipment) } ?? []
        longTransactionId = scanResults.longTransactionId.value
        subtotalMatches = scanResults.subtotalMatches
        eReceiptEmailProvider = scanResults.ereceiptEmailProvider
        eReceiptEmailId = scanResults.ereceiptEmailId
        eReceiptAuthenticated = scanResults.ereceiptAuthenticated
        instacartShopper = scanResults.isInstacartShopper
        eReceiptComponentEmails = scanResults.ereceiptComponentEmails.map{ results in
            RspReceipt(scanResults: results)
        }
        duplicate = scanResults.isDuplicate
        fraudulent = scanResults.isFraudulent
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds ?? []
        merchantMatchGuess = scanResults.merchantGuess
        productsPendingLookup = scanResults.productsPendingLookup
        qualifiedPromotions = scanResults.qualifiedPromotions.map { promotion in RspPromotion(promotion: promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualifiedPromotions.map { promotion in RspPromotion(promotion: promotion) } ?? []
        if let extendedFields = scanResults.extendedFields {
            var extendedFieldsDictionary = JSObject()
            for entry in extendedFields {
                extendedFieldsDictionary[entry.key as! String] = entry.value as? any JSValue
            }
            self.extendedFields = extendedFieldsDictionary
        } else {
            self.extendedFields = nil
        }
        if let eReceiptAdditionalFees = scanResults.ereceiptAdditionalFees {
            var additionalFeesDictionary = JSObject()
            for entry in eReceiptAdditionalFees {
                additionalFeesDictionary[entry.key] = entry.value
            }
            self.eReceiptAdditionalFees = additionalFeesDictionary
        } else {
            self.eReceiptAdditionalFees = nil
        }
        purchaseType = scanResults.purchaseType
        channel = scanResults.channel.value
        eReceiptFulfilledBy = scanResults.ereceiptFulfilledBy
        eReceiptPOSSystem = scanResults.ereceiptPOSSystem
        eReceiptSubMerchant = scanResults.ereceiptSubMerchant
        barcode = scanResults.barcode
        eReceiptMerchantEmail = scanResults.ereceiptMerchantEmail
        eReceiptEmailSubject = scanResults.ereceiptEmailSubject
        eReceiptShippingCosts = scanResults.ereceiptShippingCosts
        currencyCode = scanResults.currencyCode
        clientMerchantName = scanResults.clientMerchantName.value
        loyaltyProgram = scanResults.loyaltyProgram
        paymentTerminalId = scanResults.paymentTerminalId.value
        paymentTransactionId = scanResults.paymentTransactionId.value
        combinedRawText = scanResults.combinedRawText
        //        storeCountry = scanResults.store)
        //        eReceiptShippingAddress = scanResults.ereceiptShi ReceiptShippingAddress
        //        eReceipt = scanResults.ereceipt
        //        receiptDateTime = scanResults.receiptTime
        //        loyaltyForBanner = scanResults.loyaltyForBanner
        //        submissionDate = scanResults.submissionDate
        //        eReceiptShippingStatus = scanResults.eReceiptShippingStatus
        //        qualifiedSurveys = scanResults.qualifiedSurveys.map { survey in RspSurvey(survey) } ?? []








    }

    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["receiptDate"] = receiptDate
        ret["receiptTime"] = receiptTime 
        ret["retailerId"] = Int(retailerId)
        ret["products"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() })
        ret["coupons"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() })
        ret["total"] = total
        ret["tip"] = tip
        ret["subtotal"] = subtotal
        ret["taxes"] = taxes
        ret["storeNumber"] = storeNumber
        ret["merchantName"] = merchantName
        ret["storeAddress"] = storeAddress
        ret["storeCity"] = storeCity
        ret["blinkReceiptId"] = blinkReceiptId
        ret["storeState"] = storeState
        ret["storeZip"] = storeZip
        ret["storePhone"] = storePhone
        ret["cashierId"] = cashierId
        ret["transactionId"] = transactionId
        ret["registerId"] = registerId
        ret["paymentMethods"] = JSArray(arrayLiteral: paymentMethods.map { method in method.toPluginCallResultData() })
        ret["taxId"] = taxId
        ret["mallName"] = mallName
        ret["last4cc"] = last4cc
        ret["ocrConfidence"] = ocrConfidence
//        ret["merchantSource"] = merchantSource
        ret["foundTopEdge"] = foundTopEdge
        ret["foundBottomEdge"] = foundBottomEdge
        ret["eReceiptOrderNumber"] = eReceiptOrderNumber
        ret["eReceiptOrderStatus"] = eReceiptOrderStatus
        ret["eReceiptRawHtml"] = eReceiptRawHtml
        ret["shipments"] = JSArray(arrayLiteral: shipments.map { shipment in shipment.toPluginCallResultData() })
        ret["longTransactionId"] = longTransactionId
        ret["subtotalMatches"] = subtotalMatches
        ret["eReceiptEmailProvider"] = eReceiptEmailProvider
        ret["eReceiptEmailId"] = eReceiptEmailId
        ret["eReceiptAuthenticated"] = eReceiptAuthenticated
        ret["instacartShopper"] = instacartShopper
        ret["eReceiptComponentEmails"] = JSArray(arrayLiteral: eReceiptComponentEmails.map { email in email.toPluginCallResultData()})
        ret["duplicate"] = duplicate
        ret["fraudulent"] = fraudulent
        ret["duplicateBlinkReceiptIds"] = JSArray(duplicateBlinkReceiptIds)
        ret["merchantMatchGuess"] = merchantMatchGuess
        ret["productsPendingLookup"] = productsPendingLookup
        ret["qualifiedPromotions"] = JSArray(arrayLiteral: qualifiedPromotions.map { promo in promo.toPluginCallResultData() })
        ret["unqualifiedPromotions"] = JSArray(arrayLiteral: unqualifiedPromotions.map { promo in promo.toPluginCallResultData() })
        ret["extendedFields"] = extendedFields
        ret["eReceiptAdditionalFees"] = eReceiptAdditionalFees
        ret["purchaseType"] = purchaseType
        ret["channel"] = channel
        ret["eReceiptFulfilledBy"] = eReceiptFulfilledBy
        ret["eReceiptPOSSystem"] = eReceiptPOSSystem
        ret["eReceiptSubMerchant"] = eReceiptSubMerchant
        ret["barcode"] = barcode
        ret["eReceiptMerchantEmail"] = eReceiptMerchantEmail
        ret["eReceiptEmailSubject"] = eReceiptEmailSubject
        ret["eReceiptShippingCosts"] = eReceiptShippingCosts
        ret["currencyCode"] = currencyCode
        ret["clientMerchantName"] = clientMerchantName
        ret["loyaltyProgram"] = loyaltyProgram
        ret["merchantSources"] = JSArray(merchantSources)
        ret["paymentTerminalId"] = paymentTerminalId
        ret["paymentTransactionId"] = paymentTransactionId
        ret["combinedRawText"] = combinedRawText
        //        ret["storeCountry"] = storeCountry
        //        ret["eReceiptShippingAddress"] = eReceiptShippingAddress
        //        ret["eReceipt"] = eReceipt
        //        ret["receiptDateTime"] = receiptDateTime?.hashValue
        //        ret["loyaltyForBanner"] = loyaltyForBanner
        //        ret["submissionDate"] = submissionDate as JSValue
        //        ret["eReceiptShippingStatus"] = eReceiptShippingStatus
        //        ret["qualifiedSurveys"] = JSArray(arrayLiteral: qualifiedSurveys.map { survey in survey })
        return ret
    }
}
