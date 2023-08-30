/*
 * Copyright (c) TIKI Inc.
 * MIT license.  See LICENSE file in root directory.
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
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqScan
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async


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
    fun login(call: PluginCall) {
        val source = call.data.getString("source")
        val username = call.data.getString("username")
        val password = call.data.getString("password")
        if (source.isNullOrEmpty()) {
            call.reject("Provide source in login request")
        } else if (username.isNullOrEmpty()) {
            call.reject("Provide username in login request")
        } else if (password.isNullOrEmpty()) {
            call.reject("Provide password in login request")
        } else {
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> receiptCapture.email.login(call, account, activity)
                AccountTypeEnum.RETAILER -> receiptCapture.retailer.login(call, account, context)
            }
        }
    }

    @PluginMethod
    fun logout(call: PluginCall){
        val source = call.data.getString("source")
        val username = call.data.getString("username")
        val password = call.data.getString("password")
        if(source.isNullOrEmpty() && username.isNullOrEmpty()){
            receiptCapture.retailer.flush(call)
            receiptCapture.email.flush(call)
        } else if(!source.isNullOrEmpty() && !username.isNullOrEmpty()){
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> {
                    if(password.isNullOrEmpty()){
                        call.reject("Password is required for email logout.")
                    }else {
                        receiptCapture.email.remove(call, account)
                    }
                }
                AccountTypeEnum.RETAILER -> {
                    receiptCapture.retailer.remove(call, account.accountCommon)
                }
            }
        } else if(source.isNullOrEmpty() && !username.isNullOrEmpty()){
            call.reject("Provide source in logout request.")
        } else if(!source.isNullOrEmpty() && username.isNullOrEmpty()){
            val accountCommon = AccountCommon.fromString(source)
            if (accountCommon.type == AccountTypeEnum.RETAILER){
                receiptCapture.retailer.remove(call, accountCommon)
            }else{
                call.reject("Provide username in email logout request.")
            }
        }
    }

    @PluginMethod
    fun accounts(call: PluginCall){
        MainScope().async {
            val list = mutableListOf<Account>()
            val emails = receiptCapture.email.accounts().await()
            val retailers = receiptCapture.retailer.accounts().await()
            emails.forEach {list.add(it)}
            retailers.forEach {list.add(it)}
            call.resolve(Account.toRspList(list))
        }
    }

    @PluginMethod
    fun scan(call: PluginCall) {
        val req = ReqScan(call.data)
        if(req.account == null) {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> receiptCapture.email.scrape(call)
                ScanTypeEnum.RETAILER -> receiptCapture.retailer.orders(call)
                ScanTypeEnum.PHYSICAL -> scanPhysical(call)
                ScanTypeEnum.ONLINE -> {
                    receiptCapture.email.scrape(call)
                    receiptCapture.retailer.orders(call)
                }
            }
        } else {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> receiptCapture.email.scrape(call, req.account)
                ScanTypeEnum.RETAILER -> receiptCapture.retailer.orders(call, req.account)
               else -> call.reject("invalid scan type for account")
            }
        }
    }

    private fun scanPhysical(call: PluginCall) {
        if (getPermissionState("camera") != PermissionState.GRANTED) {
            requestPermissionForAlias("camera", call, "onCameraPermission")
        }else{
            val intent: Intent = receiptCapture.physical.open(call, context)
            startActivityForResult(call, intent, "onScanResult")
        }
    }

    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) = receiptCapture.physical.onResult(call, result)

    @PermissionCallback
    private fun onCameraPermission(call: PluginCall) {
        if (getPermissionState("camera") == PermissionState.GRANTED) scanPhysical(call)
        else call.reject("Permission is required to physical a receipt")
    }
}
