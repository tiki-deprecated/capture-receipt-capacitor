package com.mytiki.sdk.capture.receipt.capacitor

import junit.framework.TestCase
import org.junit.Test

class AccountCommonTest {
    @Test
    fun testToString() {
        val account = AccountCommon.ACME_MARKETS
        val expected = "ACME_MARKETS"
        val result = account.toString()
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun testFromString() {
        val firstResult = AccountCommon.fromSource("AMAZON")
        TestCase.assertEquals(AccountCommon.AMAZON, firstResult)
        lateinit var error: Exception
        try {
            AccountCommon.fromSource("NOT_EXISTING_VALUE")
        } catch (err: Exception){
            error = err
        }
        assert(!error.message.isNullOrEmpty())

    }

}