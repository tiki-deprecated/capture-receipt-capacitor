/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

struct RspProduct : Rsp {
    private let productNumber: RspStringType?
    private let description: RspStringType?
    private let quantity: RspFloatType?
    private let unitPrice: RspFloatType?
    private let unitOfMeasure: RspStringType?
    private let totalPrice: RspFloatType?
    private let fullPrice: Float
    private let line: Int
    private let productName: String?
    private let brand: String?
    private let category: String?
    private let size: String?
    private let rewardsGroup: String?
    private let competitorRewardsGroup: String?
    private let upc: String?
    private let imageUrl: String?
    private let shippingStatus: String?
    private let additionalLines: List<RspAdditionalLine>
    private let priceAfterCoupons: RspFloatType?
    private let voided: Bool
    private let probability: Double
    private let sensitive: Bool
    private let possibleProducts: List<RspProduct>
    private let subProducts: List<RspProduct>
    private let added: Bool
    private let blinkReceiptBrand: String?
    private let blinkReceiptCategory: String?
    private let extendedFields: JSONObject?
    private let fuelType: String?
    private let descriptionPrefix: RspStringType?
    private let descriptionPostfix: RspStringType?
    private let skuPrefix: RspStringType?
    private let skuPostfix: RspStringType?
    private let attributes: List<JSObject>
    private let sector: String?
    private let department: String?
    private let majorCategory: String?
    private let subCategory: String?
    private let itemType: String?
    
    init (product: Product){
        productNumber = RspStringType.opt(product.productNumber())
        self.description = RspStringType.opt(product.description())
        self.quantity = RspFloatType.opt(product.quantity())
        self.unitPrice = RspFloatType.opt(product.unitPrice())
        self.unitOfMeasure = RspStringType.opt(product.unitOfMeasure())
        self.totalPrice = RspFloatType.opt(product.totalPrice())
        fullPrice = product.fullPrice()
        line = product.line()
        productName = product.productName()
        brand = product.brand()
        category = product.category()
        size = product.size()
        rewardsGroup = product.rewardsGroup()
        competitorRewardsGroup = product.competitorRewardsGroup()
        upc = product.upc()
        imageUrl = product.imageUrl()
        shippingStatus = product.shippingStatus()
        additionalLines =
            product.additionalLines()?.map { additionalLine -> RspAdditionalLine(additionalLine) }
                ?? emptyList()
        priceAfterCoupons = RspFloatType.opt(product.priceAfterCoupons())
        voided = product.voided()
        probability = product.probability()
        sensitive = product.sensitive()
        possibleProducts = product.possibleProducts()?.map { prd -> RspProduct(prd) } ?? emptyList()
        subProducts = product.subProducts()?.map { prd -> RspProduct(prd) } ?? emptyList()
        added = product.added()
        blinkReceiptBrand = product.blinkReceiptBrand()
        blinkReceiptCategory = product.blinkReceiptCategory()
        fuelType = product.fuelType()
        descriptionPrefix = RspStringType.opt(product.descriptionPrefix())
        descriptionPostfix = RspStringType.opt(product.descriptionPostfix())
        skuPrefix = RspStringType.opt(product.skuPrefix())
        skuPostfix = RspStringType.opt(product.skuPostfix())
        sector = product.sector()
        department = product.department()
        majorCategory = product.majorCategory()
        subCategory = product.subCategory()
        itemType = product.itemType()
        extendedFields =  (product.extendedFields() != null) ? {
            val; extendedFields = JSObject()
            product.extendedFields()
                .forEach { entry -> extendedFields.put(entry.key, entry.value) }
            extendedFields
        } : nil
        attributes = (product.attributes() != null) ? {
            product.attributes()!!.map { attr ->
                val; json = JSONObject()
                attr.forEach { entry -> json.put(entry.key, entry.value) }
                json
            }
        } : emptyList()
    }
    
    func toJson() -> JSObject {
        JSObject.updateValue("productNumber", productNumber?.toJson())
        JSObject.updateValue("description", description?.toJson())
        JSObject.updateValue("quantity", quantity?.toJson())
        JSObject.updateValue("unitPrice", unitPrice?.toJson())
        JSObject.updateValue("unitOfMeasure", unitOfMeasure?.toJson())
        JSObject.updateValue("totalPrice", totalPrice?.toJson())
        JSObject.updateValue("fullPrice", fullPrice)
        JSObject.updateValue("line", line)
        JSObject.updateValue("productName", productName)
        JSObject.updateValue("brand", brand)
        JSObject.updateValue("category", category)
        JSObject.updateValue("size", size)
        JSObject.updateValue("rewardsGroup", rewardsGroup)
        JSObject.updateValue("competitorRewardsGroup", competitorRewardsGroup)
        JSObject.updateValue("upc", upc)
        JSObject.updateValue("imageUrl", imageUrl)
        JSObject.updateValue("shippingStatus", shippingStatus)
        JSObject.updateValue("additionalLines", JSONArray(additionalLines.map { lineJSObject.updateValueline.toJson() }))
        JSObject.updateValue("priceAfterCoupons", priceAfterCoupons?.toJson())
        JSObject.updateValue("voided", voided)
        JSObject.updateValue("probability", probability)
        JSObject.updateValue("sensitive", sensitive)
        JSObject.updateValue("possibleProducts", JSONArray(possibleProducts.map { prdJSObject.updateValueprd.toJson() }))
        JSObject.updateValue("subProducts", JSONArray(subProducts.map { prd -> prd.toJson() }))
        JSObject.updateValue("added", added)
        JSObject.updateValue("blinkReceiptBrand", blinkReceiptBrand)
        JSObject.updateValue("blinkReceiptCategory", blinkReceiptCategory)
        JSObject.updateValue("extendedFields", extendedFields)
        JSObject.updateValue("fuelType", fuelType)
        JSObject.updateValue("descriptionPrefix", descriptionPrefix?.toJson())
        JSObject.updateValue("descriptionPostfix", descriptionPostfix?.toJson())
        JSObject.updateValue("skuPrefix", skuPrefix?.toJson())
        JSObject.updateValue("skuPostfix", skuPostfix?.toJson())
        JSObject.updateValue("attributes", JSONArray(attributes))
        JSObject.updateValue("sector", sector)
        JSObject.updateValue("department", department)
        JSObject.updateValue("majorCategory", majorCategory)
        JSObject.updateValue("subCategory", subCategory)
        JSObject.updateValue("itemType", itemType)
    }
}
