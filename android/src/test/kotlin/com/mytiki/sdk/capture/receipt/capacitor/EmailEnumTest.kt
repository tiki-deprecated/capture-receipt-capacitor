package com.mytiki.sdk.capture.receipt.capacitor

import com.mytiki.sdk.capture.receipt.capacitor.email.EmailEnum
import org.junit.Assert.assertEquals
import org.junit.Test

class EmailEnumTest {

    @Test
    fun testToString() {
        val firstResult = EmailEnum.GMAIL.toString()
        assertEquals("GMAIL", firstResult)
    }


    @Test
    fun testFromString() {
        val firstResult = EmailEnum.fromString("AOL")
        assertEquals(EmailEnum.AOL, firstResult)

        lateinit var error: Exception
        try {
            EmailEnum.fromString("NOT_EXISTING_VALUE")
        } catch (err: Exception) {
            error = err
        }
        assert(!error.message.isNullOrEmpty())
    }
}
