package com.mytiki.sdk.capture.receipt.capacitor
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mytiki.sdk.capture.receipt.capacitor.fixtures.PluginCallBuilder
import io.mockk.unmockkAll
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ReceiptCaptureTest {
    private val receiptCapture = ReceiptCapture()
    private lateinit var appContext: Context

    private lateinit var licenseKey: String
    private lateinit var productKey: String
    private lateinit var retailerUsername: String
    private lateinit var retailerPassword: String
    private lateinit var retailerSource: String

    private lateinit var callInit: PluginCallBuilder
    private lateinit var respInit: JSONObject


    @Before
//    fun setUp() = runTest{
//        MockKAnnotations.init(this)
//        appContext = InstrumentationRegistry.getInstrumentation().context;
//        licenseKey = InstrumentationRegistry.getArguments().getString("licenseKey")!!
//        productKey = InstrumentationRegistry.getArguments().getString("productKey")!!
//
//        callInit = PluginCallBuilder(
//            JSONObject()
//                .put("licenseKey", licenseKey)
//                .put("productKey", productKey)
//        )
//
//        receiptCapture.initialize(callInit.build(), appContext)
//        respInit = callInit.complete.await()
//    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testInitialize() = runTest{
        TestCase.assertEquals(true, respInit.get("isInitialized"))
    }

    @Test
    fun testLogin()= runTest{
        //       TODO create Login test
    }

    @Test
    fun testLogout() {
        //       TODO create Logout test
    }

    @Test
    fun testAccounts() = runBlocking {
        //       TODO create Accounts test
    }

    @Test
    fun testScan() {
        //       TODO create Scan test
    }
}
