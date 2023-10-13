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
struct JSProduct {
    /// The product number, if available.
    private let productNumber: JSStringType?
    /// The product description, if available.
    private let description: JSStringType?
    /// The quantity of the product, if available.
    private let quantity: JSFloatType?
    /// The unit price of the product, if available.
    private let unitPrice: JSFloatType?
    /// The unit of measure for the product, if available.
    private let unitOfMeasure: JSStringType?
    /// The total price of the product, if available.
    private let totalPrice: JSFloatType?
    /// The full price of the product, if available.
    private let fullPrice: JSFloatType
    /// The product name, if available.
    private let productName: String?
    /// The brand of the product, if available.
    private let brand: String?
    /// The category of the product, if available.
    private let category: String?
    /// The size of the product, if available.
    private let size: String?
    /// The UPC code for the product
    private let upc: String?
    /// The URL of the product image, if available.
    private let imageUrl: String?
    /// The shipping status of the product, if available.
    private let shippingStatus: String?
    /// Additional lines associated with the product.
    private let additionalLines: [JSAdditionalLine]?
    /// The price of the product after applying coupons, if available.
    private let priceAfterCoupons: JSFloatType?
    /// Indicates if the product is voided.
    private let voided: Bool?
    /// The probability associated with the product.
    private let probability: Float?
    /// Indicates if the product is sensitive.
    private let sensitive: Bool?
    /// Possible sub-products associated with the product.
    private let possibleProducts: [JSProduct]
    /// Sub-products of the product.
    private let subProducts: [JSProduct]
    /// Indicates if the product is user-added.
    private let added: Bool?
    /// The brand according to BlinkReceipt.
    private let blinkReceiptBrand: String?
    /// The category according to BlinkReceipt.
    private let blinkReceiptCategory: String?
    /// A map of extended fields associated with the product.
    private let extendedFields: JSObject?
    /// The fuel type of the product.
    private let fuelType: String?
    /// The description prefix, if available.
    private let descriptionPrefix: JSStringType?
    /// The description postfix, if available.
    private let descriptionPostfix: JSStringType?
    /// The SKU prefix, if available.
    private let skuPrefix: JSStringType?
    /// The SKU postfix, if available.
    private let skuPostfix: JSStringType?
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
        productNumber = JSStringType(stringType: product.productNumber)
        description = JSStringType(string: product.description)
        quantity = JSFloatType(floatType: product.quantity)
        unitPrice = JSFloatType(floatType: product.unitPrice)
        unitOfMeasure = JSStringType(stringType: product.unitOfMeasure)
        totalPrice = JSFloatType(floatType: product.totalPrice)
        fullPrice = JSFloatType(floatType: product.fullPrice)
        productName = product.productName
        brand = product.brand
        category = product.category
        size = product.size
        upc = product.upc
        imageUrl = product.imgUrl
        shippingStatus = product.shippingStatus
        additionalLines = product.additionalLines?.map { additionalLine in JSAdditionalLine(additionalLine: additionalLine) } ?? []
        priceAfterCoupons = JSFloatType(floatType: product.priceAfterCoupons)
        voided = product.isVoided
        probability = product.probability
        sensitive = product.isSensitive
        possibleProducts = product.possibleProducts?.map { prd in JSProduct(product: prd) } ?? []
        subProducts = product.subProducts?.map { prd in JSProduct(product: prd) } ?? []
        added = product.userAdded
        blinkReceiptBrand = product.brand
        blinkReceiptCategory = product.category
        extendedFields = product.extendedFields != nil ? {
            var ret = JSObject()
            product.extendedFields!.forEach { (key: AnyHashable, value: Any) in
                ret.updateValue(value as! any JSValue, forKey: key as! String)
            }
            return ret
        }() : nil
        fuelType = product.fuelType
        descriptionPrefix = JSStringType(stringType: product.prodDescPrefix)
        descriptionPostfix = JSStringType(stringType: product.prodDescPostfix)
        skuPrefix = JSStringType(stringType: product.prodNumPrefix)
        skuPostfix = JSStringType(stringType: product.prodNumPostfix)
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
    func toJSObject() -> JSObject {
        var ret = JSObject()
        ret["productNumber"] = productNumber?.toJSObject()
        ret["description"] = description?.toJSObject()
        ret["quantity"] = quantity?.toJSObject()
        ret["unitPrice"] = unitPrice?.toJSObject()
        ret["unitOfMeasure"] = unitOfMeasure?.toJSObject()
        ret["totalPrice"] = totalPrice?.toJSObject()
        ret["fullPrice"] = fullPrice.toJSObject()
        ret["productName"] = productName
        ret["brand"] = brand
        ret["category"] = category
        ret["size"] = size
        ret["imageUrl"] = imageUrl
        ret["shippingStatus"] = shippingStatus
        ret["additionalLines"] = additionalLines
        ret["priceAfterCoupons"] = priceAfterCoupons?.toJSObject()
        ret["voided"] = voided
        ret["probability"] = probability
        ret["sensitive"] = sensitive
        ret["possibleProducts"] = possibleProducts
        ret["subProducts"] = subProducts
        ret["added"] = added
        ret["blinkReceiptBrand"] = blinkReceiptBrand
        ret["blinkReceiptCategory"] = blinkReceiptCategory
        ret["fuelType"] = fuelType
        ret["descriptionPrefix"] = descriptionPrefix?.toJSObject()
        ret["descriptionPostfix"] = descriptionPostfix?.toJSObject()
        ret["skuPrefix"] = skuPrefix?.toJSObject()
        ret["skuPostfix"] = skuPostfix?.toJSObject()
        ret["sector"] = sector
        ret["department"] = department
        ret["majorCategory"] = majorCategory
        ret["subCategory"] = subCategory
        ret["itemType"] = itemType
        return ret
    }
}
