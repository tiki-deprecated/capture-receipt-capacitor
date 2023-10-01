/*
 * RspProduct Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing product information.

 This struct is used to convey product details, including product number, description, quantity, pricing, brand, category, and more.
 */
struct RspPossibleProduct: Rsp {
    /// The product number, if available.
    private let productNumber: String?
    /// The product description, if available.
    private let description: String?
    /// The quantity of the product, if available.
    private let quantity: Float?
    /// The unit price of the product, if available.
    private let unitPrice: Float?
    /// The unit of measure for the product, if available.
    private let unitOfMeasure: String?
    /// The total price of the product, if available.
    private let totalPrice: Float?
    /// The full price of the product, if available.
    private let fullPrice: Float?
    /// The product name, if available.
    private let productName: String?
    /// The brand of the product, if available.
    private let brand: String?
    /// The category of the product, if available.
    private let category: String?
    /// The shipping status of the product, if available.
    private let shippingStatus: String?
    /// Additional lines associated with the product.
    private let additionalLines: [RspAdditionalLine]?
    /// The price of the product after applying coupons, if available.
    private let priceAfterCoupons: Float?
    /// Indicates if the product is voided.
    private let voided: Bool?
    /// The probability associated with the product.
    private let probability: Float?
    /// Indicates if the product is sensitive.
    private let sensitive: Bool?
    /// Possible sub-products associated with the product.
    private let possibleProducts: [RspProduct]?
    /// Sub-products of the product.
    private let subProducts: [RspProduct]?
    /// Indicates if the product is user-added.
    private let added: Bool?
    /// The brand according to BlinkReceipt.
    private let blinkReceiptBrand: String?
    /// The category according to BlinkReceipt.
    private let blinkReceiptCategory: String?
    /// The fuel type of the product.
    private let fuelType: String?
    /// The description prefix, if available.
    private let descriptionPrefix: String?
    /// The description postfix, if available.
    private let descriptionPostfix: String?
    /// The SKU prefix, if available.
    private let skuPrefix: String?
    /// The SKU postfix, if available.
    private let skuPostfix: String?
    /// The sector of the product.
    private let sector: String?
    /// The department of the product.
    private let department: String?
    /// The major category of the product.
    private let majorCategory: String?
    /// The sub-category of the product.
    private let subCategory: String?
    /// The item type of the product.
    private let itemType: String?

    /**
     Initializes an `RspProduct` struct.

     - Parameters:
        - product: The `BRProduct` object containing product information.
     */
    init(product: BRProduct) {
        productNumber = product.productNumber?.value
        description = product.description
        quantity = product.quantity?.value
        unitPrice = product.unitPrice?.value
        unitOfMeasure = product.unitOfMeasure?.value
        totalPrice = product.totalPrice?.value
        fullPrice = product.fullPrice?.value
        productName = product.productName
        brand = product.brand
        category = product.category
        shippingStatus = product.shippingStatus
        additionalLines = product.additionalLines?.map { additionalLine in RspAdditionalLine(additionalLine: additionalLine) } ?? []
        priceAfterCoupons = product.priceAfterCoupons?.value
        voided = product.isVoided
        probability = product.probability
        sensitive = product.isSensitive
        possibleProducts = product.possibleProducts?.map { prd in RspProduct(product: prd) } ?? []
        subProducts = product.subProducts?.map { prd in RspProduct(product: prd) } ?? []
        added = product.userAdded
        blinkReceiptBrand = product.brand
        blinkReceiptCategory = product.category
        fuelType = product.fuelType
        descriptionPrefix = product.prodDescPrefix?.value
        descriptionPostfix = product.prodDescPostfix?.value
        skuPrefix = product.prodNumPrefix?.value
        skuPostfix = product.prodNumPostfix?.value
        sector = product.sector
        department = product.department
        majorCategory = product.majorCategory
        subCategory = product.subCategory
        itemType = product.itemType
    }

    /**
     Converts the `RspProduct` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing product information in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["productNumber"] = productNumber
        ret["description"] = description
        ret["quantity"] = quantity
        ret["unitPrice"] = unitPrice
        ret["unitOfMeasure"] = unitOfMeasure
        ret["totalPrice"] = totalPrice
        ret["fullPrice"] = fullPrice
        ret["productName"] = productName
        ret["brand"] = brand
        ret["category"] = category
//        ret["size"] = size
//        ret["imageUrl"] = imageUrl
        ret["shippingStatus"] = shippingStatus
        ret["additionalLines"] = additionalLines
        ret["priceAfterCoupons"] = priceAfterCoupons
        ret["voided"] = voided
        ret["probability"] = probability
        ret["sensitive"] = sensitive
        ret["possibleProducts"] = possibleProducts
        ret["subProducts"] = subProducts
        ret["added"] = added
        ret["blinkReceiptBrand"] = blinkReceiptBrand
        ret["blinkReceiptCategory"] = blinkReceiptCategory
        ret["fuelType"] = fuelType
        ret["descriptionPrefix"] = descriptionPrefix
        ret["descriptionPostfix"] = descriptionPostfix
        ret["skuPrefix"] = skuPrefix
        ret["skuPostfix"] = skuPostfix
        ret["sector"] = sector
        ret["department"] = department
        ret["majorCategory"] = majorCategory
        ret["subCategory"] = subCategory
        ret["itemType"] = itemType
        return ret
    }
}
