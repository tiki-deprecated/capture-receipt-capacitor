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
        val account = Account.fromReq(call.data)
        when (account.accountCommon.type) {
            AccountTypeEnum.EMAIL -> receiptCapture.email.login(call, account, activity)
            AccountTypeEnum.RETAILER -> receiptCapture.retailer.login(call, account, context)
        }
    }

    @PluginMethod
    fun logout(call: PluginCall){
        if(call.data.getString("source")?.isEmpty() == true && call.data.getString("username")?.isEmpty() == true){
            receiptCapture.retailer.flush(call)
            receiptCapture.email.flush(call)
        } else if(call.data.getString("source")?.isEmpty() == false && call.data.getString("username")?.isEmpty() == false){
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> {
                    receiptCapture.email.remove(call, account)
                }
                AccountTypeEnum.RETAILER -> {
                    receiptCapture.retailer.remove(call, account.accountCommon)
                }
            }
        } else if(call.data.getString("source")?.isEmpty() == false && call.data.getString("username")?.isEmpty() == true){
            call.reject("Provide username in logout request")
        } else if(call.data.getString("source")?.isEmpty() == true && call.data.getString("username")?.isEmpty() == false){
            val accountCommon = AccountCommon.fromString(call.data.getString("source")?: "")
            if (accountCommon.type == AccountTypeEnum.RETAILER){
                receiptCapture.retailer.remove(call, accountCommon)
            }else{
                call.reject("Provide source in email logout request")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
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
            val intent: Intent = receiptCapture.scan.open(call, context)
            startActivityForResult(call, intent, "onScanResult")
        }
    }

    @ActivityCallback
    private fun onScanResult(call: PluginCall, result: ActivityResult) = receiptCapture.scan.onResult(call, result)

    @PermissionCallback
    private fun onCameraPermission(call: PluginCall) {
        if (getPermissionState("camera") == PermissionState.GRANTED) scanPhysical(call)
        else call.reject("Permission is required to scan a receipt")
    }

    private fun scanEmail(call: PluginCall){
        receiptCapture.email.scrape(call)
    }

    private fun scanRetailer(call: PluginCall) {
        receiptCapture.retailer.orders(call)
    }

}
