package com.mytiki.sdk.capture.receipt.capacitor.account

/**
 * Enum with all possible account types.
 *
 * This enum represents different types of accounts, such as EMAIL and RETAILER.
 *
 * @constructor Creates an empty [AccountTypeEnum].
 */
enum class AccountTypeEnum {
    /**
     * Represents an Email account type.
     */
    EMAIL,

    /**
     * Represents a Retailer account type.
     */
    RETAILER;

    /**
     * Converts the [AccountTypeEnum] object into a string.
     *
     * @return A string representation of the [AccountTypeEnum].
     */
    override fun toString() = this.name

    companion object {
        /**
         * Finds and returns an [AccountTypeEnum] with the specified [stringValue].
         *
         * This function searches for an [AccountTypeEnum] with a name that matches the given [stringValue].
         *
         * @param stringValue The name of the desired [AccountTypeEnum].
         * @return The [AccountTypeEnum] matching the provided [stringValue].
         * @throws NoSuchElementException If no matching [AccountTypeEnum] is found.
         */
        fun fromString(stringValue: String) =
            AccountTypeEnum.values().first { it.name == stringValue }
    }
}
