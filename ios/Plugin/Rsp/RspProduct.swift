/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspProduct : Rsp {
    private let productNumber: String?
    private let description: String?
    private let quantity: Float?
    private let unitPrice: Float?
    private let unitOfMeasure: String?
    private let totalPrice: Float?
    private let fullPrice: Float
//    private let line: Int
    private let productName: String?
    private let brand: String?
    private let category: String?
    private let size: String?
//    private let rewardsGroup: String?
//    private let competitorRewardsGroup: String?
//    private let upc: String?
    private let imageUrl: String?
    private let shippingStatus: String?
    private let additionalLines: [RspAdditionalLine]
    private let priceAfterCoupons: Float?
    private let voided: Bool
    private let probability: Float
    private let sensitive: Bool
    private let possibleProducts: [RspProduct]
    private let subProducts: [RspProduct]
    private let added: Bool
    private let blinkReceiptBrand: String?
    private let blinkReceiptCategory: String?
//    private let extendedFields: JSObject?
    private let fuelType: String?
    private let descriptionPrefix: String?
    private let descriptionPostfix: String?
    private let skuPrefix: String?
    private let skuPostfix: String?
//    private let attributes: [JSObject]
    private let sector: String?
    private let department: String?
    private let majorCategory: String?
    private let subCategory: String?
    private let itemType: String?
    
    init (product: BRProduct){
        productNumber = product.productNumber.value
        description = product.description
        quantity = product.quantity.value
        unitPrice = product.unitPrice.value
        unitOfMeasure = product.unitOfMeasure.value
        totalPrice = product.totalPrice.value
        fullPrice = product.fullPrice.value
//        line = product.line line.value
        productName = product.productName
        brand = product.brand
        category = product.category
        size = product.size
//        rewardsGroup = product.rewardsGroup.value
        //competitorRewardsGroup = product.competitorRewardsGroup.value
        //upc = product.upc.value
        imageUrl = product.imgUrl
        shippingStatus = product.shippingStatus
        additionalLines = product.additionalLines?.map { additionalLine in RspAdditionalLine(additionalLine: additionalLine) } ?? []
        priceAfterCoupons = product.priceAfterCoupons.value
        voided = product.isVoided
        probability = product.probability
        sensitive = product.isSensitive
        possibleProducts = product.possibleProducts?.map { prd in RspProduct(product: prd) } ?? []
        subProducts = product.subProducts?.map { prd in RspProduct(product: prd) } ?? []
        added = product.userAdded
        blinkReceiptBrand = product.brand
        blinkReceiptCategory = product.category
        fuelType = product.fuelType
        descriptionPrefix = product.prodDescPrefix.value
        descriptionPostfix = product.prodDescPostfix.value
        skuPrefix = product.prodNumPrefix.value
        skuPostfix = product.prodNumPostfix.value
        sector = product.sector
        department = product.department
        majorCategory = product.majorCategory
        subCategory = product.subCategory
        itemType = product.itemType
//        extendedFields =  (product.extendedFields != nil) ?
//            var extendedFields = JSObject()
//            product.extendedFields
//            .forEach { entry in extendedFields[entry.key] = entry.value as! any JSValue }
//        : nil
//        attributes = (product.attributes != nil) ?
//            product.attributes.map { attr in
//                var json = JSObject()
//                attr.forEach { entry in json[entry.key as! String] = entry.value as! any JSValue }
//            }
//            : []
    }
    
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        var ret = JSObject()
        ret["productNumber"] = productNumber
        ret["description"] = description
        ret["quantity"] = quantity
        ret["unitPrice"] = unitPrice
        ret["unitOfMeasure"] = unitOfMeasure
        ret["totalPrice"] = totalPrice
        ret["fullPrice"] = fullPrice
//        ret["line"] = line
        ret["productName"] = productName
        ret["brand"] = brand
        ret["category"] = category
        ret["size"] = size
//        ret["rewardsGroup"] = rewardsGroup
//        ret["competitorRewardsGroup"] = competitorRewardsGroup
//        ret["upc"] = upc
        ret["imageUrl"] = imageUrl
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
//        ret["extendedFields"] = extendedFields
        ret["fuelType"] = fuelType
        ret["descriptionPrefix"] = descriptionPrefix
        ret["descriptionPostfix"] = descriptionPostfix
        ret["skuPrefix"] = skuPrefix
        ret["skuPostfix"] = skuPostfix
//        ret["attributes"] = attributes
        ret["sector"] = sector
        ret["department"] = department
        ret["majorCategory"] = majorCategory
        ret["subCategory"] = subCategory
        ret["itemType"] = itemType
        return ret
    }
}
