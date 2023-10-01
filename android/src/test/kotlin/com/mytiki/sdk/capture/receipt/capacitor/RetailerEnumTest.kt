package com.mytiki.sdk.capture.receipt.capacitor

import com.microblink.linking.AMAZON_BETA
import com.microblink.linking.BESTBUY
import com.mytiki.sdk.capture.receipt.capacitor.retailer.RetailerEnum
import org.junit.Assert.assertEquals
import org.junit.Test

class RetailerEnumTest {

    @Test
    fun testToMbInt() {
        val result = RetailerEnum.AMAZON.toMbInt()
        assertEquals(AMAZON_BETA, result)
    }

    @Test
    fun testToString() {
        val result = RetailerEnum.BESTBUY.toString()
        assertEquals("BESTBUY", result)
    }

    @Test
    fun testFromMbInt() {
        val result = RetailerEnum.fromMbInt(BESTBUY)
        assertEquals(RetailerEnum.BESTBUY, result)

        lateinit var error: Exception
        try {
            RetailerEnum.fromMbInt(987346938)
        } catch (err: Exception) {
            error = err
        }
        assert(!error.message.isNullOrEmpty())
    }

    @Test
    fun testFromString() {
        val result = RetailerEnum.fromString("TARGET")
        assertEquals(RetailerEnum.TARGET, result)

        lateinit var error: Exception
        try {
            RetailerEnum.fromString("INVALID")
        } catch (err: Exception) {
            error = err
        }
        assert(!error.message.isNullOrEmpty())
    }
}
