/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.sdk.capture.receipt.capacitor.fixtures.PluginCallBuilder
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InitializeTest {
    private val receiptCapture = ReceiptCapture()

    @Test
    fun initialize() = runTest {
        val appContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext;
        val licenseKey: String =
            InstrumentationRegistry.getArguments().getString("licenseKey")!!
        val call = PluginCallBuilder(
            JSONObject()
                .put("licenseKey", licenseKey)
        )
        receiptCapture.initialize(call.build(), appContext)
        val res: JSONObject = call.complete.await()
        TestCase.assertEquals(true, res.get("isInitialized"))
    }
}