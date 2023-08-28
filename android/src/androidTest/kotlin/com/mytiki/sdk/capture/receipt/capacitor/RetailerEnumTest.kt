package com.mytiki.sdk.capture.receipt.capacitor

import com.microblink.linking.*
import junit.framework.TestCase.assertEquals
import org.junit.Test


class RetailerEnumTest {
    @Test
    fun testFromMbInt() {
        val retailer = RetailerEnum.fromMbInt(WALMART_CA)
        assertEquals(RetailerEnum.WALMART_CA, retailer)
    }
    @Test
    fun testToMbInt() {
        val mbInt = RetailerEnum.STAPLES_CA.toMbInt()
        assertEquals(STAPLES_CA, mbInt)
    }
    @Test
    fun testToString() {
        val stringValue = RetailerEnum.STAPLES_CA.toString()
        assertEquals("STAPLES_CA", stringValue)
    }
    @Test
    fun testFromString() {
        val retailer = RetailerEnum.fromString("STAPLES_CA")
        assertEquals(RetailerEnum.STAPLES_CA, retailer)
    }
}

