package com.mytiki.sdk.capture.receipt.capacitor

enum class TypeEnum {
    EMAIL,
    RETAILER;


    override fun toString() = this.name.lowercase()
    companion object {
        fun fromString(stringValue: String) = RetailerEnum.values().first { it.name.lowercase() == stringValue }
    }
}
