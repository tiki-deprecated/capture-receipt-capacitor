package com.mytiki.sdk.capture.receipt.capacitor

enum class AccountTypeEnum {
    EMAIL,
    RETAILER;


    override fun toString() = this.name.lowercase()
    companion object {
        fun fromString(stringValue: String) = AccountTypeEnum.values().first { it.name == stringValue }
    }
}
