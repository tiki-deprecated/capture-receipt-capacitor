package com.mytiki.sdk.capture.receipt.capacitor

import com.microblink.digital.Provider

enum class EmailEnum(val value: Provider) {
    YAHOO(Provider.YAHOO),
    OUTLOOK(Provider.OUTLOOK_SDK),
    AOL(Provider.AOL),
    GMAIL(Provider.GMAIL);


    override fun toString() = this.name
    fun toValue()  = this.value

    companion object {
        fun fromString(stringValue: String) = EmailEnum.values().first { it.name == stringValue }
        fun fromValue(stringValue: String) = EmailEnum.values().first { it.value.name == stringValue }
    }
}