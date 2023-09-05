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
 * It allows you to integrate receipt capture capabilities into your Capacitor-based mobile application.
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
     * This method initializes the receipt capture service, preparing it for use. You should call this method
     * before using any other receipt capture features.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun initialize(call: PluginCall) = receiptCapture.initialize(call, activity)

    /**
     * Logs in to the receipt capture service.
     *
     * This method allows users to log in to their receipt capture service account. Successful login is required
     * for accessing certain features and functionalities.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun login(call: PluginCall) = receiptCapture.login(call, activity)

    /**
     * Logs out of the receipt capture service.
     *
     * This method logs the user out of their receipt capture service account. It can be used to end the session
     * and secure user data.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun logout(call: PluginCall) = receiptCapture.logout(call, activity)

    /**
     * Fetches accounts associated with the receipt capture service.
     *
     * This method retrieves a list of user accounts associated with the receipt capture service.
     * It can be useful for user management and selection.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun accounts(call: PluginCall) = receiptCapture.accounts(call, activity)

    /**
     * Initiates the receipt capture process.
     *
     * This method starts the receipt capture process, launching the camera for scanning receipts.
     * It requires the camera permission to be granted. If not, it will request the permission from the user.
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
     * This callback is triggered when the receipt capture activity completes successfully or with an error.
     * It provides the result of the capture operation, including scan data and media if successful.
     *
     * @param call The Capacitor plugin call instance.
     * @param result The result of the receipt capture activity.
     */
    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) = receiptCapture.physical.onResult(call, result)

    /**
     * Callback invoked when the camera permission is requested.
     *
     * This callback is called when the plugin requests camera permission. If permission is granted, it will initiate
     * the receipt capture process. If not, it will reject the call with a permission error message.
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
