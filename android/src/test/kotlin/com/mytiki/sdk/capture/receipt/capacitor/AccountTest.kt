package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import com.mytiki.sdk.capture.receipt.capacitor.email.EmailEnum
import com.mytiki.sdk.capture.receipt.capacitor.retailer.RetailerEnum
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test

class AccountTest {
    private lateinit var accountCommon: AccountCommon
    private lateinit var data: PluginCall
    private lateinit var retailerAccount: com.microblink.linking.Account
    private lateinit var emailAccount: PasswordCredentials

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

//        AccountCommon mock
        accountCommon = mockk()
        every { accountCommon.type.name } returns "testType"
        every { accountCommon.id } returns "testSource"

//       JSObject mock
        data = mockk()

        every { data.getString("requestId") } returns "testRequestId"
        every { data.getString("id") } returns "AMAZON"
        every { data.getString("username") } returns "testUsername"
        every { data.getString("password") } returns "testPassword"
        every { data.getBoolean("isVerified") } returns true

//        Retailer Account mock
        retailerAccount = mockk()
        every { retailerAccount.retailerId } returns RetailerEnum.AMAZON.toMbInt()
        every { retailerAccount.credentials.username() } returns "testUsername"

//        Email Account mock
        emailAccount = mockk()
        every { emailAccount.provider().name } returns "GMAIL"
        every { emailAccount.username() } returns "testUsername"

    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testToRsp() {
        val account = Account(accountCommon, "testUsername", "testPassword", true)
        val jsObject = account.toRsp("testReq").getJSObject("payload")!!

        assert(jsObject.getString("username") == "testUsername")
        assert(jsObject.getString("id") == "testSource")
        assert(jsObject.getString("type") == "testType")
        assert(jsObject.getBoolean("isVerified"))
    }

    @Test
    fun testFromReq() {
        val account = Account.fromReq(data)

        assert(account.accountCommon == AccountCommon.AMAZON)
        assert(account.username == "testUsername")
        assert(account.password == "testPassword")
        assert(account.isVerified == true)
    }

    @Test
    fun testFromRetailerAccount() {
        val account = Account.fromRetailerAccount(retailerAccount)
        assert(account.accountCommon == AccountCommon.fromSource(RetailerEnum.AMAZON.toString()))
        assert(account.username == "testUsername")
    }

    @Test
    fun testFromEmailAccount() {
        val account = Account.fromEmailAccount(emailAccount)
        assert(account.accountCommon == AccountCommon.fromSource(EmailEnum.GMAIL.toString()))
        assert(account.username == "testUsername")
    }

    @Test
    fun testToRspList() {
        val account1 = Account(accountCommon, "user1")
        val account2 = Account(accountCommon, "user2")
        val accountList = listOf(account1, account2)

    }
}