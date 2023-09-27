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
import com.getcapacitor.PermissionState
import com.getcapacitor.Plugin
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

/**
 * This class provides functionality for scanning physical receipts using Microblink's SDK.
 */
class Physical {
    /**
     * Initializes the Microblink SDK with the provided configuration.
     *
     * @param req The initialization request parameters.
     * @param context The Android application context.
     * @param onError Callback function to handle initialization errors.
     * @return A [CompletableDeferred] indicating the initialization status.
     */
    fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?) -> Unit,
    ): CompletableDeferred<Unit> {
        val isInitialized = CompletableDeferred<Unit>()
        BlinkReceiptSdk.productIntelligenceKey(req.productKey)
        BlinkReceiptSdk.initialize(
            context,
            req.licenseKey,
            OnInitialize(isInitialized, onError)
        )
        return isInitialized
    }

    /**
     * Initiates the receipt scanning process.
     *
     * @param call The plugin call associated with the scanning operation.
     * @param plugin The Capacitor plugin instance.
     * @param context The Android application context.
     * @param reqPermissionsCallback Callback to request camera permissions if needed.
     */
    fun scan(call: PluginCall, plugin: Plugin, context: Context, reqPermissionsCallback: () -> Unit ) {
        if (plugin.getPermissionState("camera") != PermissionState.GRANTED) {
            reqPermissionsCallback()
        } else {
            val intent: Intent = open(context)
            plugin.startActivityForResult(call, intent, "onScanResult")
        }
    }

    /**
     * Creates an intent to open the camera for receipt scanning.
     *
     * @param context The Android application context.
     * @return An [Intent] for opening the camera scanner activity.
     */
    fun open(context: Context): Intent {
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

    /**
     * Handles the scanning result and appropriately resolves or rejects the plugin call based on the outcome.
     *
     * This method is responsible for processing the scanning result and responding to the plugin call accordingly.
     *
     * @param call The plugin call associated with the scanning operation.
     * @param result The result of the scanning activity, typically obtained from an activity result callback.
     *
     * If the scanning operation is successful (resultCode == Activity.RESULT_OK), it extracts scan results and media data
     * from the result and resolves the plugin call with a JSON representation of the scan results.
     *
     * If the scanning operation fails (resultCode != Activity.RESULT_OK), it rejects the plugin call with an error message.
     */
    fun onResult(call: PluginCall, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val scanResults: ScanResults? =
                result.data?.getParcelableExtra(CameraScanActivity.DATA_EXTRA)
            val media: Media? = result.data?.getParcelableExtra(CameraScanActivity.MEDIA_EXTRA)
            val rsp = RspScan(scanResults, null, false)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        } else {
            call.reject("Physical failed.")
        }
    }

}
