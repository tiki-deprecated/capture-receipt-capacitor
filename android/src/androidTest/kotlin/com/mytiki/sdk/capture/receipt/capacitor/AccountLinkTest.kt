package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.sdk.capture.receipt.capacitor.fixtures.PluginCallBuilder
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountLinkTest {
    private val retailer = Retailer()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun initialize() = runTest {
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
        Log.d("AccountLinking","Testing")
        val call = PluginCallBuilder(
            JSONObject()
                .put("licenseKey", licenseKey)
                .put("productKey", productKey)
                .put("username", username)
                .put("password", password)
                .put("retailerId", retailerId)
        )
        retailer.initialize(
            ReqInitialize(call.build().data),
            appContext
        ) { msg, data ->
            call.build().reject(msg, data)
        }.await()

        retailer.login(call.build())
    }
}
