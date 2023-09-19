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
import kotlin.properties.Delegates

/**
*
*A Capacitor plugin for receipt capture functionality.
*This plugin provides methods for initializing, logging in, logging out, fetching accounts, and capturing receipts.
*It allows you to integrate receipt capture capabilities into your Capacitor-based mobile application.
*@license MIT license. See LICENSE file in the root directory for more details.
*@author TIKI Inc.
*@version 1.0.0
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
    private var requestCode by Delegates.notNull<Int>()

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
     * Logs in with the specified account.
     *
     * This method allows users to log in their email or retailer (amazon, wallmart, gmail...) account.
     * Successful login is required
     * for accessing certain features and functionalities.
     * The [JSObject] from [PluginCall.data] sent through call must have source, username and password properties
     *
     * @param call The Capacitor [PluginCall] instance.
     */
    @PluginMethod
    fun login(call: PluginCall) = receiptCapture.login(call, activity){intent, reqCode ->
        requestCode = reqCode
        startActivityForResult(call, intent, "onLoginResult")
    }

    /**
     * Logs out of the receipt capture service.
     *
     * This method allows users to logout their email or retailer (amazon, wallmart, gmail...) account.
     * It can be used to end the session and secure user data.
     * To logout from a retailer account the [JSObject] from [PluginCall.data] sent through call must have a source propertie.
     * To logout from an email account the [JSObject] from [PluginCall.data] sent through call must have source and source, username and password properties.
     * To logout from all retailer and email accounts the [JSObject] from [PluginCall.data] sent through call must have be empty.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun logout(call: PluginCall) = receiptCapture.logout(call, activity)

    /**
     * Fetches accounts associated.
     *
     * This method retrieves a list of user email and retailer accounts associated.
     * It can be useful for user management and selection.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun accounts(call: PluginCall) = receiptCapture.accounts(call, activity)

    /**
     * Fetches all receipts on logged accounts or starts the physical receipt scan process.
     *
     * This method fetches all receipts on logged accounts or depending on the inputs starts the physical receipt scan process, launching the camera for scanning receipts.
     * It requires the camera permission to be granted. If not, it will request the permission from the user.
     * To Fetches receipts from a specific retailer or email account the [JSObject] from [PluginCall.data] sent through call must have a scanType, source, username and password properties.
     * To Fetches receipts from all email, retailer, both or to scan a physical one the [JSObject] from [PluginCall.data] sent through call must have a scanType property.
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
     * Callback invoked when the gmail sing in activity returns a result.
     *
     * This callback is triggered when the gmail sing in activity completes successfully or with an error.
     * It provides the result of the sing in if successful.
     *
     * @param call The Capacitor plugin call instance.
     * @param result The result of the gmail sing in.
     */
    @ActivityCallback
    private fun onLoginResult(call: PluginCall,  result: ActivityResult) {
        receiptCapture.email.onLoginResult(call, requestCode, result.resultCode, result.data)
    }

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
