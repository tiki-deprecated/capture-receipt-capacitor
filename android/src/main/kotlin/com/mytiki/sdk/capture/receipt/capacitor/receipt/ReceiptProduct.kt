/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.getcapacitor.JSObject
import com.microblink.core.FloatType
import com.microblink.core.Product
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a parsed product from a receipt using the RSP (Receipt Structure Parser) framework.
 *
 * @property productNumber The product number, if available.
 * @property description The product description, if available.
 * @property quantity The quantity of the product, if available.
 * @property unitPrice The unit price of the product, if available.
 * @property unitOfMeasure The unit of measure for the product, if available.
 * @property totalPrice The total price of the product, if available.
 * @property fullPrice The full price of the product.
 * @property line The line number of the product on the receipt.
 * @property productName The product name, if available.
 * @property brand The product brand, if available.
 * @property category The product category, if available.
 * @property size The product size, if available.
 * @property rewardsGroup The rewards group associated with the product, if available.
 * @property competitorRewardsGroup The competitor's rewards group, if available.
 * @property upc The Universal Product Code (UPC) of the product, if available.
 * @property imageUrl The URL of the product image, if available.
 * @property shippingStatus The shipping status of the product, if available.
 * @property additionalLines Additional lines of text associated with the product.
 * @property priceAfterCoupons The product price after applying coupons, if available.
 * @property voided Indicates whether the product is voided.
 * @property probability The probability score associated with the product.
 * @property sensitive Indicates whether the product is sensitive.
 * @property possibleProducts List of possible sub-products associated with the product.
 * @property subProducts List of sub-products associated with the product.
 * @property added Indicates whether the product was added.
 * @property blinkReceiptBrand The brand information from the BlinkReceipt SDK, if available.
 * @property blinkReceiptCategory The category information from the BlinkReceipt SDK, if available.
 * @property extendedFields Additional extended fields associated with the product.
 * @property fuelType The fuel type of the product, if available.
 * @property descriptionPrefix The prefix of the product description, if available.
 * @property descriptionPostfix The postfix of the product description, if available.
 * @property skuPrefix The prefix of the product SKU, if available.
 * @property skuPostfix The postfix of the product SKU, if available.
 * @property attributes List of attributes associated with the product.
 * @property sector The sector of the product, if available.
 * @property department The department of the product, if available.
 * @property majorCategory The major category of the product, if available.
 * @property subCategory The sub-category of the product, if available.
 * @property itemType The item type of the product, if available.
 */
class ReceiptProduct(product: Product) {
    private val productNumber: ReceiptStringType?
    private val description: ReceiptStringType?
    private val quantity: ReceiptFloatType?
    private val unitPrice: ReceiptFloatType?
    private val unitOfMeasure: ReceiptStringType?
    private val totalPrice: ReceiptFloatType?
    private val fullPrice: ReceiptFloatType
    private val line: Int
    private val productName: String?
    private val brand: String?
    private val category: String?
    private val size: String?
    private val rewardsGroup: String?
    private val competitorRewardsGroup: String?
    private val upc: String?
    private val imageUrl: String?
    private val shippingStatus: String?
    private val additionalLines: List<ReceiptAdditionalLine>
    private val priceAfterCoupons: ReceiptFloatType?
    private val voided: Boolean
    private val probability: Double
    private val sensitive: Boolean
    private val possibleProducts: List<ReceiptProduct>
    private val subProducts: List<ReceiptProduct>
    private val added: Boolean
    private val blinkReceiptBrand: String?
    private val blinkReceiptCategory: String?
    private val extendedFields: JSONObject?
    private val fuelType: String?
    private val descriptionPrefix: ReceiptStringType?
    private val descriptionPostfix: ReceiptStringType?
    private val skuPrefix: ReceiptStringType?
    private val skuPostfix: ReceiptStringType?
    private val attributes: List<JSONObject>
    private val sector: String?
    private val department: String?
    private val majorCategory: String?
    private val subCategory: String?
    private val itemType: String?

    init {
        productNumber = ReceiptStringType.opt(product.productNumber())
        description = ReceiptStringType.opt(product.description())
        quantity = ReceiptFloatType.opt(product.quantity())
        unitPrice = ReceiptFloatType.opt(product.unitPrice())
        unitOfMeasure = ReceiptStringType.opt(product.unitOfMeasure())
        totalPrice = ReceiptFloatType.opt(product.totalPrice())
        fullPrice = ReceiptFloatType(FloatType(product.fullPrice()))
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
            product.additionalLines()?.map { additionalLine -> ReceiptAdditionalLine(additionalLine) }
                ?: emptyList()
        priceAfterCoupons = ReceiptFloatType.opt(product.priceAfterCoupons())
        voided = product.voided()
        probability = product.probability()
        sensitive = product.sensitive()
        possibleProducts = product.possibleProducts()?.map { prd -> ReceiptProduct(prd) } ?: emptyList()
        subProducts = product.subProducts()?.map { prd -> ReceiptProduct(prd) } ?: emptyList()
        added = product.added()
        blinkReceiptBrand = product.blinkReceiptBrand()
        blinkReceiptCategory = product.blinkReceiptCategory()
        fuelType = product.fuelType()
        descriptionPrefix = ReceiptStringType.opt(product.descriptionPrefix())
        descriptionPostfix = ReceiptStringType.opt(product.skuPostfix())
        skuPrefix = ReceiptStringType.opt(product.skuPrefix())
        skuPostfix = ReceiptStringType.opt(product.skuPostfix())
        sector = product.sector()
        department = product.department()
        majorCategory = product.majorCategory()
        subCategory = product.subCategory()
        itemType = product.itemType()
        extendedFields = if (product.extendedFields() != null) {
            val extendedFields = JSObject()
            product.extendedFields()
                ?.forEach { entry -> extendedFields.put(entry.key, entry.value) }
            extendedFields
        } else null
        attributes = if (product.attributes() != null) {
            product.attributes()!!.map { attr ->
                val json = JSObject()
                attr.forEach { entry -> json.put(entry.key, entry.value) }
                json
            }
        } else emptyList()
    }

    /**
     * Converts the RSP product to a JSON representation.
     *
     * @return A JSON object representing the RSP product.
     */
    fun toJS(): JSObject =
        JSObject()
            .put("productNumber", productNumber?.toJS())
            .put("description", description?.toJS())
            .put("quantity", quantity?.toJS())
            .put("unitPrice", unitPrice?.toJS())
            .put("unitOfMeasure", unitOfMeasure?.toJS())
            .put("totalPrice", totalPrice?.toJS())
            .put("fullPrice", fullPrice)
            .put("line", line)
            .put("productName", productName)
            .put("brand", brand)
            .put("category", category)
            .put("size", size)
            .put("rewardsGroup", rewardsGroup)
            .put("competitorRewardsGroup", competitorRewardsGroup)
            .put("upc", upc)
            .put("imageUrl", imageUrl)
            .put("shippingStatus", shippingStatus)
            .put("additionalLines", JSONArray(additionalLines.map { line -> line.toJS() }))
            .put("priceAfterCoupons", priceAfterCoupons?.toJS())
            .put("voided", voided)
            .put("probability", probability)
            .put("sensitive", sensitive)
            .put("possibleProducts", JSONArray(possibleProducts.map { prd -> prd.toJS() }))
            .put("subProducts", JSONArray(subProducts.map { prd -> prd.toJS() }))
            .put("added", added)
            .put("blinkReceiptBrand", blinkReceiptBrand)
            .put("blinkReceiptCategory", blinkReceiptCategory)
            .put("extendedFields", extendedFields)
            .put("fuelType", fuelType)
            .put("descriptionPrefix", descriptionPrefix?.toJS())
            .put("descriptionPostfix", descriptionPostfix?.toJS())
            .put("skuPrefix", skuPrefix?.toJS())
            .put("skuPostfix", skuPostfix?.toJS())
            .put("attributes", JSONArray(attributes))
            .put("sector", sector)
            .put("department", department)
            .put("majorCategory", majorCategory)
            .put("subCategory", subCategory)
            .put("itemType", itemType)

    companion object {
        /**
         * Creates an optional [ReceiptProduct] from a [Product] instance.
         *
         * @param product The [Product] instance to create an [ReceiptProduct] from.
         * @return An [ReceiptProduct] instance or null if the input is null.
         */
        fun opt(product: Product?): ReceiptProduct? =
            if (product != null) ReceiptProduct(product) else null
    }
}
