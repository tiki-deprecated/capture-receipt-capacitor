/*
 * Copyright (c) TIKI Inc.
 * MIT license.  See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.plugin

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.CaptureReceipt
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.plugin.req.Req
import com.mytiki.sdk.capture.receipt.capacitor.plugin.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.plugin.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.plugin.req.ReqScan
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.Rsp
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.RspAccount
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.RspError
import com.mytiki.sdk.capture.receipt.capacitor.plugin.rsp.RspReceipt
import com.mytiki.sdk.capture.receipt.capacitor.readLong
import com.mytiki.sdk.capture.receipt.capacitor.writeLong

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tiki-capture-receipt")

/**
 * A Capacitor plugin for receipt capture functionality.
 *
 * This plugin provides methods for initializing, logging in, logging out, fetching accounts, and capturing receipts.
 * It allows you to integrate receipt capture capabilities into your Capacitor-based mobile application.
 *
 * @license MIT license. See LICENSE file in the root directory for more details.
 * @author TIKI Inc.
 * @version 1.0.0
 */
@CapacitorPlugin(
    name = "CaptureReceipt"
)
class CaptureReceiptPlugin : Plugin() {
    private val captureReceipt = CaptureReceipt()

    /**
     * Initializes the receipt capture functionality.
     *
     * This method initializes the receipt capture service, preparing it for use. You should call this method
     * before using any other receipt capture features.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun initialize(call: PluginCall) {
        val request = ReqInitialize(call)
        captureReceipt.initialize(context, request.licenseKey, request.productKey, {
            call.resolve()
        }, { error -> call.reject(error) })

        context.readLong({}){
            context.writeLong(0L)
        }
    }

    /**
     * Logs in with the specified account.
     *
     * This method allows users to log in their email or retailer (amazon, wallmart, gmail...) account.
     * Successful login is required for accessing certain features and functionalities.
     *
     * @param call The Capacitor [PluginCall] instance.
     */
    @PluginMethod
    fun login(call: PluginCall) {
        val req = ReqAccount(call)
        if (req.password.isNullOrBlank()) {
            call.reject("Provide password in login request")
        } else {
            captureReceipt.login(
                activity,
                req.username,
                req.password,
                req.accountCommon.id,
                { account -> call.resolve(account.toRsp("login")) },
                { msg -> call.reject(msg) }
            )

        }
    }

    /**
     * Logs out of the receipt capture service.
     *
     * This method allows users to logout their email or retailer (amazon, wallmart, gmail...) account.
     * It can be used to end the session and secure user data.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun logout(call: PluginCall) {
        val id = call.data.getString("id")
        val username = call.data.getString("username")
        if (id.isNullOrBlank() && username.isNullOrBlank()) {
            captureReceipt.logout(activity, null,
                onError = {
                    call.reject(it)
                },
                onComplete = {
                    call.resolve()
                })
        }else if (!id.isNullOrBlank() && !username.isNullOrBlank()) {
            captureReceipt.logout(activity, Account.fromReq(call),
                onError = {
                    call.reject(it)
                },
                onComplete = {
                    call.resolve()
                })
        }
        if (id.isNullOrBlank() && !username.isNullOrBlank()) {
            call.reject("Account type id is required for specific account logout.")
        } else if (!id.isNullOrBlank() && username.isNullOrBlank()) {
            call.reject("Username is required for specific account logout.")
        }
    }

    /**
     * Fetches accounts associated.
     *
     * This method retrieves a list of user email and retailer accounts associated.
     * It can be useful for user management and selection.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun accounts(call: PluginCall) {
        val req = Req(call)
        captureReceipt.accounts(
            context,
            { account: Account -> onAccount(req.requestId, account) },
            { error -> onError(req.requestId, error) },
            { onComplete(req.requestId) }
        )
    }

    /**
     * Fetches all receipts on logged accounts or starts the physical receipt scan process.
     *
     * This method fetches all receipts on logged accounts or depending on the inputs starts the physical receipt scan process, launching the camera for scanning receipts.
     * It requires the camera permission to be granted. If not, it will request the permission from the user.
     * To Fetches receipts from a specific retailer or email account the [JSObject] from [PluginCall.data] sent through call must have a scanType, id, username, and password properties.
     * To Fetches receipts from all email, retailer, both, or to scan a physical one the [JSObject] from [PluginCall.data] sent through call must have a scanType property.
     *
     * @param call The Capacitor plugin call instance.
     */
    @PluginMethod
    fun scan(call: PluginCall) {
        val req = ReqScan(call)
        captureReceipt.scan(activity, req.dayCutOff,
            onComplete = { onComplete(req.requestId) },
            onError = { msg -> onError(req.requestId, msg) },
            onReceipt = { receipt -> onReceipt(req.requestId, receipt) }
        )
    }


    /**
     * Callback for receipt scanning.
     *
     * @param scan The scanned results.
     */
    private fun onReceipt(requestId: String, scan: ScanResults? = null) {
        val data = if (scan != null) {
            RspReceipt(requestId, scan).toJS()
        } else {
            JSObject()
        }
        notifyListeners("onCapturePluginResult", data)
    }

    /**
     * Callback for account information.
     *
     * @param account The account information.
     */
    private fun onAccount(requestId: String, account: Account) {
        val data = RspAccount(requestId, account).toJS()
        notifyListeners("onCapturePluginResult", data)
    }

    private fun onComplete(requestId: String) {
        val data = Rsp(requestId, PluginEvent.onComplete).toJS()
        notifyListeners("onCapturePluginResult", data)
    }

    /**
     * Callback for error handling.
     *
     * @param message The error message.
     */
    private fun onError(requestId: String, message: String) {
        val data = RspError(requestId, message).toJS()
        notifyListeners("onCapturePluginResult", data)
    }


}
