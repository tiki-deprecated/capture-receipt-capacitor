/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing receipt information.

 This struct is used to convey details about a receipt, including various receipt fields, such as date, time, products, coupons, totals, and more.
 */
public class RspReceipt : Rsp{
    /// The date of the receipt, if available.
    private let receiptDate: String?
    /// The time of the receipt, if available.
    private let receiptTime: String?
    /// The retailer's identifier.
    private let retailerId: UInt
    /// An array of products associated with the receipt.
    private let products: [JSProduct]
    /// An array of coupons associated with the receipt.
    private let coupons: [JSCoupon]
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
    private let paymentMethods: [JSPaymentMethod]
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
    private let shipments: [JSShipment]
    /// The long transaction identifier, if available.
    private let longTransactionId: String?
    /// Indicates if the subtotal matches expectations.
    private let subtotalMatches: Bool?
    /// The email provider associated with the eReceipt, if available.
    private let eReceiptEmailProvider: String?
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
    private let qualifiedPromotions: [JSPromotion]
    /// An array of unqualified promotions associated with the receipt.
    private let unqualifiedPromotions: [JSPromotion]
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
    init(requestId: String, scanResults: BRScanResults) {
        // Initialization logic to map values from BRScanResults to struct properties.
        receiptDate = scanResults.receiptDate?.value
        receiptTime = scanResults.receiptTime?.value
        retailerId = scanResults.retailerId.rawValue
        products = scanResults.products?.map { product in JSProduct(product: product) } ?? []
        coupons = scanResults.coupons?.map { coupon in JSCoupon(coupon: coupon) } ?? []
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
        paymentMethods = scanResults.paymentMethods?.map { payment in JSPaymentMethod(paymentMethod: payment.method?.value, cardType: payment.cardType?.value, cardIssuer: payment.cardIssuer?.value, amount: payment.amount.value) } ?? []
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
        shipments = scanResults.shipments?.map { shipment in JSShipment(shipment: shipment) } ?? []
        longTransactionId = scanResults.longTransactionId?.value
        subtotalMatches = scanResults.subtotalMatches
        eReceiptEmailProvider = scanResults.ereceiptEmailProvider
        eReceiptAuthenticated = scanResults.ereceiptAuthenticated
        instacartShopper = scanResults.isInstacartShopper
        if(scanResults.ereceiptComponentEmails != nil){
            eReceiptComponentEmails = scanResults.ereceiptComponentEmails?.map{ results in
                RspReceipt(requestId: requestId, scanResults: results)
            } ?? []
        }else {
            eReceiptComponentEmails = []
        }
        duplicate = scanResults.isDuplicate
        fraudulent = scanResults.isFraudulent
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds ?? []
        merchantMatchGuess = scanResults.merchantGuess
        productsPendingLookup = scanResults.productsPendingLookup
        qualifiedPromotions = scanResults.qualifiedPromotions?.map { promotion in JSPromotion(promotion: promotion) } ?? []
        unqualifiedPromotions = scanResults.unqualifiedPromotions?.map { promotion in JSPromotion(promotion: promotion) } ?? []
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
        super.init(requestId: requestId, event: .onReceipt)
    }

    
    /**
     Converts the `RspReceipt` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing receipt information in a format suitable for a Capacitor plugin call result.
     */
    override func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var payload = getPayload()
        var ret = super.toPluginCallResultData()
        ret["payload"] = payload
        return ret
    }
    
    
    private func getPayload() -> JSObject{
        var payload = JSObject()
        payload["receiptDate"] = receiptDate
        payload["receiptTime"] = receiptTime
        payload["retailerId"] = Int(retailerId)
        payload["products"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() } )
        payload["coupons"] = JSArray(arrayLiteral: products.map { prd in prd.toPluginCallResultData() } )
        payload["total"] = total
        payload["tip"] = tip
        payload["subtotal"] = subtotal
        payload["taxes"] = taxes
        payload["storeNumber"] = storeNumber
        payload["merchantName"] = merchantName
        payload["storeAddress"] = storeAddress
        payload["storeCity"] = storeCity
        payload["blinkReceiptId"] = blinkReceiptId
        payload["storeState"] = storeState
        payload["storeZip"] = storeZip
        payload["storePhone"] = storePhone
        payload["cashierId"] = cashierId
        payload["transactionId"] = transactionId
        payload["registerId"] = registerId
        payload["paymentMethods"] = JSArray(arrayLiteral: paymentMethods.map { method in method.toPluginCallResultData() })
        payload["taxId"] = taxId
        payload["mallName"] = mallName
        payload["last4cc"] = last4cc
        payload["ocrConfidence"] = ocrConfidence
        payload["foundTopEdge"] = foundTopEdge
        payload["foundBottomEdge"] = foundBottomEdge
        payload["eReceiptOrderNumber"] = eReceiptOrderNumber
        payload["eReceiptOrderStatus"] = eReceiptOrderStatus
        payload["eReceiptRawHtml"] = eReceiptRawHtml
        payload["shipments"] = JSArray(arrayLiteral: shipments.map { shipment in shipment.toPluginCallResultData() })
        payload["longTransactionId"] = longTransactionId
        payload["subtotalMatches"] = subtotalMatches
        payload["eReceiptEmailProvider"] = eReceiptEmailProvider
        payload["eReceiptAuthenticated"] = eReceiptAuthenticated
        payload["instacartShopper"] = instacartShopper
        payload["eReceiptComponentEmails"] = JSArray(arrayLiteral: eReceiptComponentEmails.map { email in email.toPluginCallResultData()} )
        payload["duplicate"] = duplicate
        payload["fraudulent"] = fraudulent
        payload["duplicateBlinkReceiptIds"] = JSArray(duplicateBlinkReceiptIds )
        payload["merchantMatchGuess"] = merchantMatchGuess
        payload["productsPendingLookup"] = productsPendingLookup
        payload["qualifiedPromotions"] = JSArray(arrayLiteral: qualifiedPromotions.map { promo in promo.toPluginCallResultData() } )
        payload["unqualifiedPromotions"] = JSArray(arrayLiteral: unqualifiedPromotions.map { promo in promo.toPluginCallResultData() } )
        payload["extendedFields"] = extendedFields
        payload["eReceiptAdditionalFees"] = eReceiptAdditionalFees
        payload["purchaseType"] = purchaseType
        payload["channel"] = channel
        payload["eReceiptFulfilledBy"] = eReceiptFulfilledBy
        payload["eReceiptPOSSystem"] = eReceiptPOSSystem
        payload["eReceiptSubMerchant"] = eReceiptSubMerchant
        payload["barcode"] = barcode
        payload["eReceiptMerchantEmail"] = eReceiptMerchantEmail
        payload["eReceiptEmailSubject"] = eReceiptEmailSubject
        payload["eReceiptShippingCosts"] = eReceiptShippingCosts
        payload["currencyCode"] = currencyCode
        payload["clientMerchantName"] = clientMerchantName
        payload["loyaltyProgram"] = loyaltyProgram
        payload["merchantSources"] = JSArray(merchantSources )
        payload["paymentTerminalId"] = paymentTerminalId
        payload["paymentTransactionId"] = paymentTransactionId
        payload["combinedRawText"] = combinedRawText
        return payload
    }
}
