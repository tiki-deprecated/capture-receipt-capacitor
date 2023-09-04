/*
 * Copyright (c) TIKI Inc.
 * MIT license.  See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.Manifest
import androidx.activity.result.ActivityResult
import com.getcapacitor.PermissionState
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.Permission
import com.getcapacitor.annotation.PermissionCallback

/**
 * A Capacitor plugin for receipt capture functionality.
 *
 * This plugin provides methods for initializing, logging in, logging out, fetching accounts, and capturing receipts.
 */
@CapacitorPlugin(
    name = "ReceiptCapture",
    permissions = [
        Permission(
            alias = "camera",
            strings = [Manifest.permission.CAMERA]
        )
    ]
)
class ReceiptCapturePlugin : Plugin() {
    private val receiptCapture = ReceiptCapture()

    /**
     * Initializes the receipt capture functionality.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun initialize(call: PluginCall) = receiptCapture.initialize(call, activity)

    /**
     * Logs in to the receipt capture service.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun login(call: PluginCall) = receiptCapture.login(call, activity)

    /**
     * Logs out of the receipt capture service.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun logout(call: PluginCall) = receiptCapture.logout(call, activity)

    /**
     * Fetches accounts associated with the receipt capture service.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun accounts(call: PluginCall) = receiptCapture.accounts(call, activity)

    /**
     * Initiates the receipt capture process.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun scan(call: PluginCall) = receiptCapture.scan(this, call, activity) {
        requestPermissionForAlias("camera", call, "onCameraPermission")
    }

    /**
     * Callback invoked when the receipt capture activity returns a result.
     *
     * @param call The Capacitor plugin call instance.
     * @param result The result of the receipt capture activity.
     */
    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) = receiptCapture.physical.onResult(call, result)

    /**
     * Callback invoked when the camera permission is requested.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PermissionCallback
    private fun onCameraPermission(call: PluginCall) {
        if (getPermissionState("camera") == PermissionState.GRANTED) {
            receiptCapture.scan(this, call, activity) {
                requestPermissionForAlias("camera", call, "onCameraPermission")
            }
        } else {
            call.reject("Permission is required to capture a receipt")
        }
    }
}
