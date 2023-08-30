/*
 * Copyright (c) TIKI Inc.
 * MIT license.  See LICENSE file in root directory.
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

    @PluginMethod
    fun initialize(call: PluginCall) = receiptCapture.initialize(call, activity)

    @PluginMethod
    fun login(call: PluginCall) = receiptCapture.login(call, activity)

    @PluginMethod
    fun logout(call: PluginCall) = receiptCapture.logout(call, activity)

    @PluginMethod
    fun accounts(call: PluginCall) = receiptCapture.accounts(call, activity)

    @PluginMethod
    fun scan(call: PluginCall) = receiptCapture.scan(this, call, activity) {
        requestPermissionForAlias("camera", call, "onCameraPermission")
    }

    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) = receiptCapture.physical.onResult(call, result)

    @PermissionCallback
    private fun onCameraPermission(call: PluginCall) {
        if (getPermissionState("camera") == PermissionState.GRANTED) receiptCapture.scan(this, call, activity) {
            requestPermissionForAlias("camera", call, "onCameraPermission")
        }
        else call.reject("Permission is required to physical a receipt")
    }
}
