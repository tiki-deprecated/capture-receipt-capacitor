package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.sdk.capture.receipt.capacitor.fixtures.PluginCallBuilder
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.minutes

@RunWith(AndroidJUnit4::class)
class RetailerTest {
    private val retailer = Retailer()

    @Test
    fun accountLinking() = runTest {
        val appContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val licenseKey: String =
            InstrumentationRegistry.getArguments().getString("licenseKey")!!
        val productKey: String =
            InstrumentationRegistry.getArguments().getString("productKey")!!
        val username: String =
            InstrumentationRegistry.getArguments().getString("username")!!
        val password: String =
            InstrumentationRegistry.getArguments().getString("password")!!
        val retailerId: Int = RetailerEnum.amazon_beta.value
//        Retailer Initializer
        val callInit = PluginCallBuilder(
            JSONObject()
                .put("licenseKey", licenseKey)
                .put("productKey", productKey)
        ).build()
        retailer.initialize(ReqInitialize(callInit.data), appContext) { msg, data -> callInit.reject(msg, data) }
            .await()

//        Retailer Account Test
        val callAccount = PluginCallBuilder(
            JSONObject()
                .put("username", username)
                .put("password", password)
                .put("retailerId", retailerId)
        )
        retailer.login(callAccount.build())
        val resAccount = callAccount.complete.await()
        TestCase.assertEquals(true, resAccount.get("isAccountLinked"))
        TestCase.assertEquals(username, resAccount.get("username"))
        TestCase.assertEquals(retailerId, resAccount.get("retailerId"))
    }

    @Test()
    fun grabOrders() = runTest(timeout = 1.minutes){
        val appContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val licenseKey: String =
            InstrumentationRegistry.getArguments().getString("licenseKey")!!
        val productKey: String =
            InstrumentationRegistry.getArguments().getString("productKey")!!
        val username: String =
            InstrumentationRegistry.getArguments().getString("username")!!
        val password: String =
            InstrumentationRegistry.getArguments().getString("password")!!
        val retailerId: Int = RetailerEnum.amazon_beta.value

        val callInit = PluginCallBuilder(
            JSONObject()
                .put("licenseKey", licenseKey)
                .put("productKey", productKey)
        ).build()
        retailer.initialize(ReqInitialize(callInit.data), appContext) { msg, data -> callInit.reject(msg, data) }
            .await()
        val callAccount = PluginCallBuilder(
            JSONObject()
                .put("username", username)
                .put("password", password)
                .put("retailerId", retailerId)
        )
        retailer.login(callAccount.build())
        val account = callAccount.complete.await()
//        Retailer Orders Test
        val callOrders = PluginCallBuilder(
            JSONObject()
                .put("username", account)
                .put("retailerId", retailerId)
        )
        retailer.orders(appContext, callOrders.build())
        val resOrders = callOrders.complete.await()
        TestCase.assertEquals(true, resOrders.get("isAccountLinked"))
        TestCase.assertEquals(true, resOrders.get("isOrders"))
    }
}
