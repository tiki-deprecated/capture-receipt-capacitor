/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.Manifest
import android.content.Intent
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
    fun initialize(call: PluginCall) = receiptCapture.initialize(call, context)

    @PluginMethod
    fun scan(call: PluginCall) {
        if (getPermissionState("camera") != PermissionState.GRANTED)
            requestPermissionForAlias("camera", call, "onCameraPermission")
        else startScan(call)
    }

    @PluginMethod
    fun loginWithEmail(call: PluginCall) = receiptCapture.email.login(call, activity)

    @PluginMethod
    fun scrapeEmail(call: PluginCall) = receiptCapture.email.scape(call)

    @PluginMethod
    fun verifyEmail(call: PluginCall) = receiptCapture.email.accounts(call)

    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) =
        receiptCapture.scan.onResult(call, result)

    @PermissionCallback
    private fun onCameraPermission(call: PluginCall) {
        if (getPermissionState("camera") == PermissionState.GRANTED) startScan(call)
        else call.reject("Permission is required to scan a receipt")
    }

    private fun startScan(call: PluginCall) {
        val intent: Intent = receiptCapture.scan.open(call, context)
        startActivityForResult(call, intent, "onScanResult")
    }
}