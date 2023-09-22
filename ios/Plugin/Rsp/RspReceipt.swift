/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing receipt information.

 This struct is used to convey details about a receipt, including various receipt fields, such as date, time, products, coupons, totals, and more.
 */
public struct RspReceipt: Rsp {
    /// The date of the receipt, if available.
    private let receiptDate: String?
    /// The time of the receipt, if available.
    private let receiptTime: String?
    /// The retailer's identifier.
    private let retailerId: UInt
    /// An array of products associated with the receipt.
    private let products: [RspProduct]
    /// An array of coupons associated with the receipt.
    private let coupons: [RspCoupon]
    /// The total amount of the receipt, if available.
    private let total: Float?
    /// The tip amount, if available.
    private let tip: Float?
    /// The subtotal amount, if available.
    private let subtotal: Float?
    /// The taxes amount, if available.
    private let taxes: Float?
    /// The store number, if available.
    private let storeNumber: String?
    /// The merchant name, if available.
    private let merchantName: String?
    /// The store address, if available.
    private let storeAddress: String?
    /// The store city, if available.
    private let storeCity: String?
    /// The unique identifier for the receipt.
    private let blinkReceiptId: String?
    /// The store state, if available.
    private let storeState: String?
    /// The store zip code, if available.
    private let storeZip: String?
    /// The store phone number, if available.
    private let storePhone: String?
    /// The cashier's identifier, if available.
    private let cashierId: String?
    /// The transaction identifier, if available.
    private let transactionId: String?
    /// The register identifier, if available.
    private let registerId: String?
    /// An array of payment methods used for the receipt.
    private let paymentMethods: [RspPaymentMethod]
    /// The tax identifier, if available.
    private let taxId: String?
    /// The mall name, if available.
    private let mallName: String?
    /// The last four digits of the credit card, if available.
    private let last4cc: String?
    /// The OCR (Optical Character Recognition) confidence level, if available.
    private let ocrConfidence: Float?
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
    private let shipments: [RspShipment]
    /// The long transaction identifier, if available.
    private let longTransactionId: String?
    /// Indicates if the subtotal matches expectations.
    private let subtotalMatches: Bool?
    /// The email provider associated with the eReceipt, if available.
    private let eReceiptEmailProvider: String?
    /// The email identifier associated with the eReceipt, if available.
    private let eReceiptEmailId: String?
    /// Indicates if the eReceipt was successfully authenticated.
    private let eReceiptAuthenticated: Bool?
    /// Indicates if the shopper is an Instacart shopper.
    private let instacartShopper: Bool?
    /// An array of component emails associated with the eReceipt.
    private let eReceiptComponentEmails: [RspReceipt]
    /// Indicates if the receipt is a duplicate.
    private let duplicate: Bool?
    /// Indicates if the receipt is fraudulent.
    private let fraudulent: Bool?
    /// An array of duplicate BlinkReceipt IDs.
    private let duplicateBlinkReceiptIds: [String]
    /// A guess at the merchant's name.
    private let merchantMatchGuess: String?
    /// The number of products pending lookup.
    private let productsPendingLookup: Int?
    /// An array of qualified promotions associated with the receipt.
    private let qualifiedPromotions: [RspPromotion]
    /// An array of unqualified promotions associated with the receipt.
    private let unqualifiedPromotions: [RspPromotion]
    /// Extended fields of the receipt, if available.
    private let extendedFields: JSObject?
    /// Additional fees associated with the eReceipt, if available.
    private let eReceiptAdditionalFees: JSObject?
    /// The purchase type, if available.
    private let purchaseType: String?
    /// The channel, if available.
    private let channel: String?
    /// The entity that fulfilled the eReceipt, if available.
    private let eReceiptFulfilledBy: String?
    /// The point of sale (POS) system used for the eReceipt, if available.
    private let eReceiptPOSSystem: String?
    /// The sub-merchant associated with the eReceipt, if available.
    private let eReceiptSubMerchant: String?
    /// The barcode associated with the receipt, if available.
    private let barcode: String?
    /// The email address of the merchant, if available.
    private let eReceiptMerchantEmail: String?
    /// The subject of the eReceipt email, if available.
    private let eReceiptEmailSubject: String?
    /// The shipping costs associated with the eReceipt, if available.
    private let eReceiptShippingCosts: Float?
    /// The currency code, if available.
    private let currencyCode: String?
    /// The client's merchant name, if available.
    private let clientMerchantName: String?
    /// Indicates if the receipt is part of a loyalty program.
    private let loyaltyProgram: Bool?
    /// An array of merchant sources associated with the receipt.
    private let merchantSources: [NSNumber]
    /// The payment terminal identifier, if available.
    private let paymentTerminalId: String?
    /// The payment transaction identifier, if available.
    private let paymentTransactionId: String?
    /// The combined raw text of the receipt, if available.
    private let combinedRawText: String?

    /**
     Initializes an `RspReceipt` struct.

     - Parameters:
        - scanResults: The `BRScanResults` object containing receipt information.
     */
    init(scanResults: BRScanResults) {
        // Initialization logic to map values from BRScanResults to struct properties.
        receiptDate = scanResults.receiptDate?.value
        receiptTime = scanResults.receiptTime?.value
        retailerId = scanResults.retailerId.rawValue
        products = scanResults.products?.map { product in RspProduct(product: product) } ?? []
        coupons = scanResults.coupons?.map { coupon in RspCoupon(coupon: coupon) } ?? []
        total = scanResults.total?.value
        tip = scanResults.tip?.value
        subtotal = scanResults.subtotal?.value
        taxes = scanResults.taxes?.value
        storeNumber = scanResults.storeNumber?.value
        merchantName = scanResults.merchantName?.value
        storeAddress = scanResults.storeAddress?.value
        storeCity = scanResults.storeCity?.value
        blinkReceiptId = scanResults.blinkReceiptId
        storeState = scanResults.storeState?.value
        storeZip = scanResults.storeZip?.value
        storePhone = scanResults.storePhone?.value
        cashierId = scanResults.cashierId?.value
        transactionId = scanResults.transactionId?.value
        registerId = scanResults.registerId?.value
        paymentMethods = scanResults.paymentMethods?.map { payment in RspPaymentMethod(paymentMethod: payment.method?.value, cardType: payment.cardType?.value, cardIssuer: payment.cardIssuer?.value, amount: payment.amount.value) } ?? []
        taxId = scanResults.taxId?.value
        mallName = scanResults.mallName?.value
        last4cc = scanResults.last4CC?.value
        ocrConfidence = scanResults.ocrConfidence
        merchantSources = scanResults.merchantSources ?? []
        foundTopEdge = scanResults.foundTopEdge
        foundBottomEdge = scanResults.foundBottomEdge
        eReceiptOrderNumber = scanResults.ereceiptOrderNumber
        eReceiptOrderStatus = scanResults.ereceiptOrderStatus
        eReceiptRawHtml = scanResults.ereceiptRawHTML
        shipments = scanResults.shipments?.map { shipment in RspShipment(shipment: shipment) } ?? []
        longTransactionId = scanResults.longTransactionId?.value
        subtotalMatches = scanResults.subtotalMatches
        eReceiptEmailProvider = scanResults.ereceiptEmailProvider
        eReceiptEmailId = scanResults.ereceiptEmailId
        eReceiptAuthenticated = scanResults.ereceiptAuthenticated
        instacartShopper = scanResults.isInstacartShopper
        if(scanResults.ereceiptComponentEmails != nil){
            eReceiptComponentEmails = scanResults.ereceiptComponentEmails?.map{ results in
                RspReceipt(scanResults: results)
            } ?? []
        }else {
            eReceiptComponentEmails = []
        }
        duplicate = scanResults.isDuplicate
        fraudulent = scanResults.isFraudulent
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds ?? []
        merchantMatchGuess = scanResults.merchantGuess
        productsPendingLookup = scanResults.productsPendingLookup
        qualifiedPromotions = scanResults.qualifiedPromotions?.map { promotion in RspPromotion(promotion: promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualifiedPromotions?.map { promotion in RspPromotion(promotion: promotion) } ?? []
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
        channel = scanResults.channel?.value
        eReceiptFulfilledBy = scanResults.ereceiptFulfilledBy
        eReceiptPOSSystem = scanResults.ereceiptPOSSystem
        eReceiptSubMerchant = scanResults.ereceiptSubMerchant
        barcode = scanResults.barcode
        eReceiptMerchantEmail = scanResults.ereceiptMerchantEmail
        eReceiptEmailSubject = scanResults.ereceiptEmailSubject
        eReceiptShippingCosts = scanResults.ereceiptShippingCosts
        currencyCode = scanResults.currencyCode
        clientMerchantName = scanResults.clientMerchantName?.value
        loyaltyProgram = scanResults.loyaltyProgram
        paymentTerminalId = scanResults.paymentTerminalId?.value
        paymentTransactionId = scanResults.paymentTransactionId?.value
        combinedRawText = scanResults.combinedRawText
    }

    
    /**
     Converts the `RspReceipt` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing receipt information in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        // Mapping struct properties to dictionary values.
        ret["receiptDate"] = receiptDate
        ret["receiptTime"] = receiptTime 
        ret["retailerId"] = Int(retailerId)
        ret["products"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() } )
        ret["coupons"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() } )
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
        ret["eReceiptComponentEmails"] = JSArray(arrayLiteral: eReceiptComponentEmails.map { email in email.toPluginCallResultData()} )
        ret["duplicate"] = duplicate
        ret["fraudulent"] = fraudulent
        ret["duplicateBlinkReceiptIds"] = JSArray(duplicateBlinkReceiptIds )
        ret["merchantMatchGuess"] = merchantMatchGuess
        ret["productsPendingLookup"] = productsPendingLookup
        ret["qualifiedPromotions"] = JSArray(arrayLiteral: qualifiedPromotions.map { promo in promo.toPluginCallResultData() } )
        ret["unqualifiedPromotions"] = JSArray(arrayLiteral: unqualifiedPromotions.map { promo in promo.toPluginCallResultData() } )
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
        ret["merchantSources"] = JSArray(merchantSources )
        ret["paymentTerminalId"] = paymentTerminalId
        ret["paymentTransactionId"] = paymentTransactionId
        ret["combinedRawText"] = combinedRawText
        return ret
    }
}
