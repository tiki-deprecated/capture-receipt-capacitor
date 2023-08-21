package com.mytiki.sdk.capture.receipt.capacitor

import com.microblink.linking.*
import junit.framework.TestCase.assertEquals
import org.junit.Test


class RetailerEnumTest {

    @Test
    fun testFromInt() {
        val retailer = RetailerEnum.fromInt(WALMART_CA)
        assertEquals(RetailerEnum.walmart_ca, retailer)
    }

    @Test
    fun testToInt() {
        val intValue = RetailerEnum.toInt(RetailerEnum.staples_ca)
        assertEquals(STAPLES_CA, intValue)
    }

    @Test
    fun testToString() {
        val stringValue = RetailerEnum.toString(RetailerEnum.seamless)
        assertEquals(SEAMLESS.toString(), stringValue)
    }

    @Test
    fun testFromString() {
        val retailer = RetailerEnum.fromString(STAPLES_CA.toString())
        assertEquals(RetailerEnum.staples_ca, retailer)
    }
}

