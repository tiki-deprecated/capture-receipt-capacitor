package com.mytiki.sdk.capture.receipt.capacitor

/**
 * An enumeration representing different types of scans.
 *
 * This enum defines four types of scans: EMAIL, RETAILER, PHYSICAL, and ONLINE.
 * It provides methods for converting between the enum values and their string representations.
 */
enum class ScanTypeEnum {
    /**
     * Represents an email scan.
     */
    EMAIL,

    /**
     * Represents a retailer scan.
     */
    RETAILER,

    /**
     * Represents a physical scan.
     */
    PHYSICAL,

    /**
     * Represents an online scan.
     */
    ONLINE;

    /**
     * Converts the enum value to its string representation.
     *
     * @return The string representation of the enum value.
     */
    override fun toString() = this.name

    companion object {
        /**
         * Converts a string value to the corresponding com.mytiki.sdk.capture.receipt.capacitor.ScanTypeEnum value.
         *
         * @param stringValue The string representation of the com.mytiki.sdk.capture.receipt.capacitor.ScanTypeEnum value.
         * @return The com.mytiki.sdk.capture.receipt.capacitor.ScanTypeEnum value corresponding to the input string.
         * @throws IllegalArgumentException if no matching enum value is found for the input string.
         */
        fun fromString(stringValue: String) =
            ScanTypeEnum.values().first { it.name == stringValue }
    }
}
