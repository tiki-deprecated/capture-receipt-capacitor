/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.BlinkReceiptSdk
import com.microblink.FrameCharacteristics
import com.microblink.Media
import com.microblink.ScanOptions
import com.microblink.camera.ui.CameraScanActivity
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspScan
import kotlinx.coroutines.CompletableDeferred

class Scan {
    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isInitialized = CompletableDeferred<Unit>()
        BlinkReceiptSdk.productIntelligenceKey(req.productKey!!)
        BlinkReceiptSdk.initialize(
            context,
            req.licenseKey!!,
            OnInitialize(isInitialized, onError)
        )
        return isInitialized
    }

    fun open(call: PluginCall, context: Context): Intent {
        val scanOptions = ScanOptions.newBuilder()
            .detectDuplicates(true)
            .frameCharacteristics(
                FrameCharacteristics.newBuilder()
                    .storeFrames(true)
                    .compressionQuality(100)
                    .externalStorage(false).build()
            )
            .logoDetection(true)
            .build()
        val bundle = Bundle()
        bundle.putParcelable(CameraScanActivity.SCAN_OPTIONS_EXTRA, scanOptions)
        return Intent(context, CameraScanActivity::class.java)
            .putExtra(CameraScanActivity.BUNDLE_EXTRA, bundle)
    }

    fun onResult(call: PluginCall, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val scanResults: ScanResults? =
                result.data?.getParcelableExtra(CameraScanActivity.DATA_EXTRA)
            val media: Media? = result.data?.getParcelableExtra(CameraScanActivity.MEDIA_EXTRA)
            val rsp: RspScan? = RspScan.opt(scanResults)
            call.resolve(if (rsp != null) JSObject.fromJSONObject(rsp.toJson()) else JSObject())
        } else call.reject("Scan failed.")
    }
}