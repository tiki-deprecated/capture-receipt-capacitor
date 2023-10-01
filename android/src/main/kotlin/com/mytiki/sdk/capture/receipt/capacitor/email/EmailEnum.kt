package com.mytiki.sdk.capture.receipt.capacitor.email

import com.microblink.digital.Provider

/**
 * Enumeration representing common email providers and their corresponding [Provider] values.
 *
 * @property value The [Provider] value associated with the email provider.
 */
enum class EmailEnum(val value: Provider) {
    /**
     * Yahoo email provider.
     */
    YAHOO(Provider.YAHOO),

    /**
     * Outlook email provider.
     */
    OUTLOOK(Provider.OUTLOOK_SDK),

    /**
     * AOL email provider.
     */
    AOL(Provider.AOL),

    /**
     * Gmail email provider.
     */
    GMAIL(Provider.GMAIL);

    /**
     * Returns the string representation of the email provider.
     *
     * @return The name of the email provider.
     */
    override fun toString() = this.name

    companion object {
        /**
         * Converts a string representation of an email provider to the corresponding [EmailEnum] value.
         *
         * @param stringValue The string representation of the email provider.
         * @return The [EmailEnum] value corresponding to the input string.
         * @throws NoSuchElementException if the input string does not match any email provider name.
         */
        fun fromString(stringValue: String) = EmailEnum.values().first { it.name == stringValue }
    }
}
