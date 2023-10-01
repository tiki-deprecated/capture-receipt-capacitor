package com.mytiki.sdk.capture.receipt.capacitor

import com.mytiki.sdk.capture.receipt.capacitor.account.AccountTypeEnum
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountTypeEnumTest {

    @Test
    fun testToString() {
        val firstResult = AccountTypeEnum.EMAIL.toString()
        assertEquals("EMAIL", firstResult)

        val secondResult = AccountTypeEnum.RETAILER.toString()
        assertEquals("RETAILER", secondResult)
    }


    @Test
    fun testFromString() {
        val firstResult = AccountTypeEnum.fromString("EMAIL")
        assertEquals(AccountTypeEnum.EMAIL, firstResult)

        val secondResult = AccountTypeEnum.fromString("RETAILER")
        assertEquals(AccountTypeEnum.RETAILER, secondResult)

        lateinit var error: Exception
        try {
            AccountTypeEnum.fromString("NOT_EXISTING_VALUE")
        } catch (err: Exception) {
            error = err
        }
        assert(!error.message.isNullOrEmpty())
    }
}
