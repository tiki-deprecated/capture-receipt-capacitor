package com.mytiki.sdk.capture.receipt.capacitor

/**
 * Enum with all [AccountCommon] types possibilities
 *
 * @constructor Create empty Account type enum
 */
enum class AccountTypeEnum {
    EMAIL,
    RETAILER;

    /**
     * Converts the [AccountTypeEnum] object into a string
     *
     */
    override fun toString() = this.name
    companion object {
        /**
         * Finds and returns an [AccountTypeEnum] with the [AccountTypeEnum.name] equals to [stringValue]
         *
         * @param stringValue is a [AccountTypeEnum.name]
         */
        fun fromString(stringValue: String) = AccountTypeEnum.values().first { it.name == stringValue }
    }
}
