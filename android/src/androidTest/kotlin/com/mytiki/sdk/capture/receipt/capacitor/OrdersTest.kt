package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.sdk.capture.receipt.capacitor.fixtures.PluginCallBuilder
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrdersTest {
    private val retailer = Retailer()
    @Test
    fun grabOrders() = runTest {
        val appContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext;
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
                .put("username", username)
                .put("password", password)
                .put("retailerId", retailerId)
        ).build()
        retailer.initialize(ReqInitialize(callInit.data), appContext){ msg, data -> callInit.reject(msg, data) }.await()

//        Retailer Account Test
        val callAccount = PluginCallBuilder(
            JSONObject()
                .put("licenseKey", licenseKey)
                .put("productKey", productKey)
                .put("username", username)
                .put("password", password)
                .put("retailerId", retailerId)
        )
        retailer.account(callAccount.build())
        val resAccount = callAccount.complete.await()
        TestCase.assertEquals(true, resAccount.get("isAccountLinked"))
    }
}
