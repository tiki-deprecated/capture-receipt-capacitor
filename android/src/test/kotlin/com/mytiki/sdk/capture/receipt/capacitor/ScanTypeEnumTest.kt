package com.mytiki.sdk.capture.receipt.capacitor
import org.junit.Assert.assertEquals
import org.junit.Test

class ScanTypeEnumTest {

    @Test
    fun testToString() {
        val firstResult = ScanTypeEnum.EMAIL.toString()
        assertEquals("EMAIL", firstResult)

        val secondResult = ScanTypeEnum.PHYSICAL.toString()
        assertEquals("PHYSICAL", secondResult)
    }


    @Test
    fun testFromString() {
        val firstResult = ScanTypeEnum.fromString("ONLINE")
        assertEquals(ScanTypeEnum.ONLINE, firstResult)

        val secondResult = ScanTypeEnum.fromString("RETAILER")
        assertEquals(ScanTypeEnum.RETAILER, secondResult)

        lateinit var error: Exception
        try {
            ScanTypeEnum.fromString("NOT_EXISTING_VALUE")
        } catch (err: Exception){
            error = err
        }
        assert(!error.message.isNullOrEmpty())
    }
}
