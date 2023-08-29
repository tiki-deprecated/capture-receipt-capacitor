/*
 * Copyright (c) TIKI Inc.
 * MIT license.  See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResult
import androidx.annotation.RequiresApi
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

//    REFACTORED CODE
    @PluginMethod
    fun initialize(call: PluginCall) = receiptCapture.initialize(call, context)

    @PluginMethod
    fun login(call: PluginCall){
        if(
            call.data.getString("source").isNullOrEmpty() && !call.data.getString("username").isNullOrEmpty() && !call.data.getString("password").isNullOrEmpty()
        ) {
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> receiptCapture.email.login(call, account, activity)
                AccountTypeEnum.RETAILER -> receiptCapture.retailer.login(call, account, context)
            }
        } else {
            if(call.data.getString("source").isNullOrEmpty()){
                call.reject("Provide source in login request")
            }
            if(call.data.getString("username").isNullOrEmpty()){
                call.reject("Provide username in login request")
            }
            if(call.data.getString("password").isNullOrEmpty()){
                call.reject("Provide password in login request")
            }
        }

    }

    @PluginMethod
    fun logout(call: PluginCall){
        if(call.data.getString("source").isNullOrEmpty() && call.data.getString("username").isNullOrEmpty()){
            receiptCapture.retailer.flush(call)
            receiptCapture.email.flush(call)
        } else if(!call.data.getString("source").isNullOrEmpty() && !call.data.getString("username").isNullOrEmpty()){
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> {
                    receiptCapture.email.remove(call, account)
                }
                AccountTypeEnum.RETAILER -> {
                    receiptCapture.retailer.remove(call, account.accountCommon)
                }
            }
        } else if(call.data.getString("source").isNullOrEmpty() && !call.data.getString("username").isNullOrEmpty()){
            call.reject("Provide source in logout request")
        } else if(!call.data.getString("source").isNullOrEmpty() && call.data.getString("username").isNullOrEmpty()){
            val accountCommon = AccountCommon.fromString(call.data.getString("source")?: "")
            if (accountCommon.type == AccountTypeEnum.RETAILER){
                receiptCapture.retailer.remove(call, accountCommon)
            }else{
                call.reject("Provide username in email logout request")
            }
        }
    }

    @PluginMethod
    fun accounts(call: PluginCall){
        val list = mutableListOf<Account>()
        MainScope().async {
            val emails = receiptCapture.email.accounts().await()
            val retailers = receiptCapture.retailer.accounts().await()
            emails.forEach {list.add(it)}
            retailers.forEach {list.add(it)}
        }
        call.resolve(Account.toRspList(list))
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
                ScanTypeEnum.EMAIL -> receiptCapture.email.scrape(call)
                ScanTypeEnum.RETAILER -> receiptCapture.retailer.orders(call)
               else -> call.reject("invalid scan type for account")
            }
            if(req.account.accountCommon.source === AccountTypeEnum.RETAILER.name) {
                receiptCapture.retailer.orders(call, req.account)
            } else {
                receiptCapture.email.scrape(call, req.account)
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
