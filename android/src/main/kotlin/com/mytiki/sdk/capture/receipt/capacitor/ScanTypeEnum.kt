package com.mytiki.sdk.capture.receipt.capacitor

enum class ScanTypeEnum {
    EMAIL,
    RETAILER,
    PHYSICAL;


    override fun toString() = this.name.lowercase()
    companion object {
        fun fromString(stringValue: String) = ScanTypeEnum.values().first { it.name == stringValue }
    }
}
