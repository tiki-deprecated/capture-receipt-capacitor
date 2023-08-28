package com.mytiki.sdk.capture.receipt.capacitor

enum class ScanTypeEnum {
    EMAIL,
    RETAILER,
    PHYSICAL,
    ONLINE;

    override fun toString() = this.name
    companion object {
        fun fromString(stringValue: String) = ScanTypeEnum.values().first { it.name == stringValue }
    }
}
