/*
 * RspReceipt Class
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 A class representing a response containing receipt information for the ReceiptCapture plugin.
 
 This class encapsulates detailed information about a receipt, including various receipt fields such as date, time, products, coupons, totals, and more.
 */
public class Receipt : Rsp{
    /// The date of the receipt, if available.
    private let receiptDate: ModelStringType?
    /// The time of the receipt, if available.
    private let receiptTime: ModelStringType?
    /// The retailer's identifier.
    private let retailerId: ModelRetailer
    /// An array of products associated with the receipt.
    private let products: [ModelProduct]
    /// An array of coupons associated with the receipt.
    private let coupons: [ModelCoupon]
    /// The total amount of the receipt, if available.
    private let total: ModelFloatType?
    /// The tip amount, if available.
    private let tip: ModelFloatType?
    /// The subtotal amount, if available.
    private let subtotal: ModelFloatType?
    /// The taxes amount, if available.
    private let taxes: ModelFloatType?
    /// The store number, if available.
    private let storeNumber: ModelStringType?
    /// The merchant name, if available.
    private let merchantName: ModelStringType?
    /// The store address, if available.
    private let storeAddress: ModelStringType?
    /// The store city, if available.
    private let storeCity: ModelStringType?
    /// The unique identifier for the receipt.
    private let blinkReceiptId: String?
    /// The store state, if available.
    private let storeState: ModelStringType?
    /// The store zip code, if available.
    private let storeZip: ModelStringType?
    /// The store phone number, if available.
    private let storePhone: ModelStringType?
    /// The cashier's identifier, if available.
    private let cashierId: ModelStringType?
    /// The transaction identifier, if available.
    private let transactionId: ModelStringType?
    /// The register identifier, if available.
    private let registerId: ModelStringType?
    /// An array of payment methods used for the receipt.
    private let paymentMethods: [ModelPaymentMethod]
    /// The tax identifier, if available.
    private let taxId: ModelStringType?
    /// The mall name, if available.
    private let mallName: ModelStringType?
    /// The last four digits of the credit card, if available.
    private let last4cc: ModelStringType?
    /// The OCR (Optical Character Recognition) confidence level, if available.
    private let ocrConfidence: Float
    /// Indicates if the top edge of the receipt was found during processing.
    private let foundTopEdge: Bool?
    /// Indicates if the bottom edge of the receipt was found during processing.
    private let foundBottomEdge: Bool?
    /// The eReceipt order number, if available.
    private let eReceiptOrderNumber: String?
    /// The eReceipt order status, if available.
    private let eReceiptOrderStatus: String?
    /// The raw HTML content of the eReceipt, if available.
    private let eReceiptRawHtml: String?
    /// An array of shipment information associated with the receipt.
    private let shipments: [ModelShipment]
    /// The long transaction identifier, if available.
    private let longTransactionId: ModelStringType?
    /// Indicates if the subtotal matches expectations.
    private let subtotalMatches: Bool?
    /// The email provider associated with the eReceipt, if available.
    private let eReceiptEmailProvider: String?
    /// Indicates if the eReceipt was successfully authenticated.
    private let eReceiptAuthenticated: Bool?
    /// Indicates if the shopper is an Instacart shopper.
    private let instacartShopper: Bool?
    /// Indicates if the receipt is an eReceipt.
    private let eReceipt: Bool
    /// An array of component emails associated with the eReceipt.
    private let eReceiptComponentEmails: [Receipt]
    /// Indicates if the receipt is a duplicate.
    private let duplicate: Bool?
    /// Indicates if the receipt is fraudulent.
    private let fraudulent: Bool?
    /// An array of duplicate BlinkReceipt IDs.
    private let duplicateBlinkReceiptIds: [String]
    /// A guess at the merchant's name.
    private let merchantMatchGuess: String?
    /// The number of products pending lookup.
    private let productsPendingLookup: Int
    /// An array of qualified promotions associated with the receipt.
    private let qualifiedPromotions: [ModelPromotion]
    /// An array of unqualified promotions associated with the receipt.
    private let unqualifiedPromotions: [ModelPromotion]
    /// Extended fields of the receipt, if available.
    private let extendedFields: JSObject?
    /// Additional fees associated with the eReceipt, if available.
    private let eReceiptAdditionalFees: JSObject?
    /// The purchase type, if available.
    private let purchaseType: ModelStringType?
    /// The channel, if available.
    private let channel: ModelStringType?
    /// The entity that fulfilled the eReceipt, if available.
    private let eReceiptFulfilledBy: String?
    /// The shipping status of the eReceipt.
    private let eReceiptPOSSystem: String?
    /// The sub-merchant associated with the eReceipt, if available.
    private let eReceiptSubMerchant: String?
    /// An array of qualified surveys associated with the receipt.
    private let qualifiedSurveys: [ModelSurvey]
    /// The barcode associated with the receipt, if available.
    private let barcode: String?
    /// The email address of the merchant, if available.
    private let eReceiptMerchantEmail: String?
    /// The subject of the eReceipt email, if available.
    private let eReceiptEmailSubject: String?
    /// The shipping costs associated with the eReceipt, if available.
    private let eReceiptShippingCosts: Float
    /// The currency code, if available.
    private let currencyCode: String?
    /// The client's merchant name, if available.
    private let clientMerchantName: ModelStringType?
    /// Indicates if the receipt is part of a loyalty program.
    private let loyaltyProgram: Bool?
    /// An array of merchant sources associated with the receipt.
    private let merchantSources: [NSNumber]
    /// The payment terminal identifier, if available.
    private let paymentTerminalId: ModelStringType?
    /// The payment transaction identifier, if available.
    private let paymentTransactionId: ModelStringType?
    /// The combined raw text of the receipt, if available.
    private let combinedRawText: ModelStringType?

    /**
     Initializes an `RspReceipt` struct.

     - Parameters:
        - scanResults: The `BRScanResults` object containing receipt information.
     */
    init(requestId: String, scanResults: BRScanResults) {
        receiptDate = ModelStringType(stringType: scanResults.receiptDate)
        receiptTime = ModelStringType(stringType: scanResults.receiptTime)
        retailerId = ModelRetailer(retailer: scanResults.retailerId)
        products = scanResults.products?.map { product in ModelProduct(product: product) } ?? []
        coupons = scanResults.coupons?.map { coupon in ModelCoupon(coupon: coupon) } ?? []
        total = ModelFloatType(floatType: scanResults.total)
        tip = ModelFloatType(floatType: scanResults.tip)
        subtotal = ModelFloatType(floatType: scanResults.subtotal)
        taxes = ModelFloatType(floatType: scanResults.taxes)
        storeNumber = ModelStringType(stringType: scanResults.storeNumber)
        merchantName = ModelStringType(stringType: scanResults.merchantName)
        storeAddress = ModelStringType(stringType: scanResults.storeAddress)
        storeCity = ModelStringType(stringType: scanResults.storeCity)
        blinkReceiptId = scanResults.blinkReceiptId
        storeState = ModelStringType(stringType: scanResults.storeState)
        storeZip = ModelStringType(stringType: scanResults.storeZip)
        storePhone = ModelStringType(stringType: scanResults.storePhone)
        cashierId = ModelStringType(stringType: scanResults.cashierId)
        transactionId = ModelStringType(stringType: scanResults.transactionId)
        registerId = ModelStringType(stringType: scanResults.registerId)
        paymentMethods = scanResults.paymentMethods?.map { payment in ModelPaymentMethod(method: payment)} ?? []
        taxId = ModelStringType(stringType: scanResults.taxId)
        mallName = ModelStringType(stringType: scanResults.mallName)
        last4cc = ModelStringType(stringType: scanResults.last4CC)
        ocrConfidence = scanResults.ocrConfidence
        foundTopEdge = scanResults.foundTopEdge
        foundBottomEdge = scanResults.foundBottomEdge
        eReceiptOrderNumber = scanResults.ereceiptOrderNumber
        eReceiptOrderStatus = scanResults.ereceiptOrderStatus
        eReceiptRawHtml = scanResults.ereceiptRawHTML
        shipments = scanResults.shipments?.map { shipment in ModelShipment(shipment: shipment) } ?? []
        longTransactionId = ModelStringType(stringType: scanResults.longTransactionId)
        subtotalMatches = scanResults.subtotalMatches
        eReceiptEmailProvider = scanResults.ereceiptEmailProvider
        eReceiptAuthenticated = scanResults.ereceiptAuthenticated
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.ereceiptIsValid
        eReceiptComponentEmails = scanResults.ereceiptComponentEmails?.map{ res in Receipt(requestId: requestId, scanResults: res)} ?? []
        duplicate = scanResults.isDuplicate
        fraudulent = scanResults.isFraudulent
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds ?? []
        merchantMatchGuess = scanResults.merchantGuess
        productsPendingLookup = scanResults.productsPendingLookup
        qualifiedPromotions = scanResults.qualifiedPromotions?.map { promotion in ModelPromotion(promotion: promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualifiedPromotions?.map { promotion in ModelPromotion(promotion: promotion) } ?? []
        extendedFields = scanResults.extendedFields != nil ? {
            var ret = JSObject()
            scanResults.extendedFields!.forEach { (key: AnyHashable, value: Any) in
                ret.updateValue(value as! any JSValue, forKey: key as! String)
            }
            return ret
        }() : nil
        eReceiptAdditionalFees = scanResults.ereceiptAdditionalFees != nil ? {
            var ret = JSObject()
            scanResults.ereceiptAdditionalFees!.forEach { (key: String, value: String) in
                ret.updateValue(value, forKey: key)
            }
            return ret
        }() : nil
        purchaseType = ModelStringType(string: scanResults.purchaseType)
        channel = ModelStringType(stringType: scanResults.channel)
        eReceiptFulfilledBy = scanResults.ereceiptFulfilledBy
        eReceiptPOSSystem = scanResults.ereceiptPOSSystem
        eReceiptSubMerchant = scanResults.ereceiptSubMerchant
        qualifiedSurveys = scanResults.qualifiedSurveys?.map{survey in ModelSurvey(survey: survey)} ?? []
        barcode = scanResults.barcode
        eReceiptMerchantEmail = scanResults.ereceiptMerchantEmail
        eReceiptEmailSubject = scanResults.ereceiptEmailSubject
        eReceiptShippingCosts = scanResults.ereceiptShippingCosts
        currencyCode = scanResults.currencyCode
        clientMerchantName = ModelStringType(stringType: scanResults.clientMerchantName)
        loyaltyProgram = scanResults.loyaltyProgram
        merchantSources = scanResults.merchantSources ?? []
        paymentTerminalId = ModelStringType(stringType: scanResults.paymentTerminalId)
        paymentTransactionId = ModelStringType(stringType: scanResults.paymentTransactionId)
        combinedRawText = ModelStringType(string: scanResults.combinedRawText)
        super.init(requestId: requestId, event: .onReceipt)
    }

    
    /**
     Converts the `RspReceipt` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing receipt information in a format suitable for a Capacitor plugin call result.
     */
    override func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = super.toPluginCallResultData()
        ret["payload"] = toJSObject()
        return ret
    }
    
    func toJSObject() -> JSObject {
        var payload = JSObject()
        payload["receiptDate"] = receiptDate?.toJSObject()
        payload["receiptTime"] = receiptTime?.toJSObject()
        payload["retailerId"] = retailerId.toJSObject()
        payload["products"] = JSArray(arrayLiteral: products.map { prd in prd.toJSObject() } )
        payload["coupons"] = JSArray(arrayLiteral: products.map { prd in prd.toJSObject() } )
        payload["total"] = total?.toJSObject()
        payload["tip"] = tip?.toJSObject()
        payload["subtotal"] = subtotal?.toJSObject()
        payload["taxes"] = taxes?.toJSObject()
        payload["storeNumber"] = storeNumber?.toJSObject()
        payload["merchantName"] = merchantName?.toJSObject()
        payload["storeAddress"] = storeAddress?.toJSObject()
        payload["storeCity"] = storeCity?.toJSObject()
        payload["blinkReceiptId"] = blinkReceiptId
        payload["storeState"] = storeState?.toJSObject()
        payload["storeZip"] = storeZip?.toJSObject()
        payload["storePhone"] = storePhone?.toJSObject()
        payload["cashierId"] = cashierId?.toJSObject()
        payload["transactionId"] = transactionId?.toJSObject()
        payload["registerId"] = registerId?.toJSObject()
        payload["paymentMethods"] = JSArray(arrayLiteral: paymentMethods.map { method in method.toJSObject() })
        payload["taxId"] = taxId?.toJSObject()
        payload["mallName"] = mallName?.toJSObject()
        payload["last4cc"] = last4cc?.toJSObject()
        payload["ocrConfidence"] = ocrConfidence
        payload["foundTopEdge"] = foundTopEdge
        payload["foundBottomEdge"] = foundBottomEdge
        payload["eReceiptOrderNumber"] = eReceiptOrderNumber
        payload["eReceiptOrderStatus"] = eReceiptOrderStatus
        payload["eReceiptRawHtml"] = eReceiptRawHtml
        payload["shipments"] = JSArray(arrayLiteral: shipments.map { shipment in shipment.toJSObject() })
        payload["longTransactionId"] = longTransactionId?.toJSObject()
        payload["subtotalMatches"] = subtotalMatches
        payload["eReceiptEmailProvider"] = eReceiptEmailProvider
        payload["eReceiptAuthenticated"] = eReceiptAuthenticated
        payload["instacartShopper"] = instacartShopper
        payload["eReceipt"] = eReceipt
        payload["eReceiptComponentEmails"] = JSArray(arrayLiteral: eReceiptComponentEmails.map { email in email.toJSObject()} )
        payload["duplicate"] = duplicate
        payload["fraudulent"] = fraudulent
        payload["duplicateBlinkReceiptIds"] = JSArray(duplicateBlinkReceiptIds )
        payload["merchantMatchGuess"] = merchantMatchGuess
        payload["productsPendingLookup"] = productsPendingLookup
        payload["qualifiedPromotions"] = JSArray(arrayLiteral: qualifiedPromotions.map { promo in promo.toJSObject() } )
        payload["unqualifiedPromotions"] = JSArray(arrayLiteral: unqualifiedPromotions.map { promo in promo.toJSObject() } )
        payload["extendedFields"] = extendedFields
        payload["eReceiptAdditionalFees"] = eReceiptAdditionalFees
        payload["purchaseType"] = purchaseType?.toJSObject()
        payload["channel"] = channel?.toJSObject()
        payload["eReceiptFulfilledBy"] = eReceiptFulfilledBy
        payload["eReceiptPOSSystem"] = eReceiptPOSSystem
        payload["eReceiptSubMerchant"] = eReceiptSubMerchant
        payload["qualifiedSurveys"] = JSArray(arrayLiteral: qualifiedSurveys.map { survey in survey.toJSObject() })
        payload["barcode"] = barcode
        payload["eReceiptMerchantEmail"] = eReceiptMerchantEmail
        payload["eReceiptEmailSubject"] = eReceiptEmailSubject
        payload["eReceiptShippingCosts"] = eReceiptShippingCosts
        payload["currencyCode"] = currencyCode
        payload["clientMerchantName"] = clientMerchantName?.toJSObject()
        payload["loyaltyProgram"] = loyaltyProgram
        payload["merchantSources"] = JSArray(merchantSources)
        payload["paymentTerminalId"] = paymentTerminalId?.toJSObject()
        payload["paymentTransactionId"] = paymentTransactionId?.toJSObject()
        payload["combinedRawText"] = combinedRawText?.toJSObject()
        return payload
    }
}
