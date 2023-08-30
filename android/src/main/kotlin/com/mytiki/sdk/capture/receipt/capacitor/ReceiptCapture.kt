/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqScan
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspInitialized
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class ReceiptCapture {
    val physical = Physical()
    val email = Email()
    val retailer = Retailer()

    fun initialize(call: PluginCall, context: Context) {
        val req = ReqInitialize(call.data)
        MainScope().async {
            physical.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            email.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            retailer.initialize(req, context) { msg, data -> call.reject(msg, data) }.await()
            val rsp = RspInitialized(true)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        }
    }

    fun login(call: PluginCall, context: AppCompatActivity) {
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
                AccountTypeEnum.EMAIL -> email.login(call, account, context)
                AccountTypeEnum.RETAILER -> retailer.login(call, account, context)
            }
        }
    }

    fun logout(call: PluginCall, context: Context) {
        val source = call.data.getString("source")
        val username = call.data.getString("username")
        val password = call.data.getString("password")
        if(source.isNullOrEmpty() && username.isNullOrEmpty()){
            retailer.flush(call, context)
            email.flush(call)
        } else if(!source.isNullOrEmpty() && !username.isNullOrEmpty()){
            val account = Account.fromReq(call.data)
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> {
                    if(password.isNullOrEmpty()){
                        call.reject("Password is required for email logout.")
                    }else {
                        email.remove(call, account)
                    }
                }
                AccountTypeEnum.RETAILER -> {
                    retailer.remove(call, account.accountCommon, context)
                }
            }
        } else if(source.isNullOrEmpty() && !username.isNullOrEmpty()){
            call.reject("Provide source in logout request.")
        } else if(!source.isNullOrEmpty() && username.isNullOrEmpty()) {
            val accountCommon = AccountCommon.fromString(source)
            if (accountCommon.type == AccountTypeEnum.RETAILER) {
                retailer.remove(call, accountCommon, context)
            } else {
                call.reject("Provide username in email logout request.")
            }
        }
    }

    fun accounts(call: PluginCall, context: Context) {
        MainScope().async {
            val list = mutableListOf<Account>()
            val emails = email.accounts().await()
            val retailers = retailer.accounts(context).await()
            emails.forEach {list.add(it)}
            retailers.forEach {list.add(it)}
            call.resolve(Account.toRspList(list))
        }
    }

    fun scan(plugin: Plugin, call: PluginCall, context: Context, reqPermCallback: () -> Unit) {
        val req = ReqScan(call.data)
        if(req.account == null) {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> email.scrape(call)
                ScanTypeEnum.RETAILER -> retailer.orders(call, context)
                ScanTypeEnum.PHYSICAL -> physical.scan(call, plugin, context, reqPermCallback)
                ScanTypeEnum.ONLINE -> {
                    email.scrape(call)
                    retailer.orders(call, context)
                }
            }
        } else {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> email.scrape(call, req.account)
                ScanTypeEnum.RETAILER -> retailer.orders(call, req.account, context)
                else -> call.reject("invalid scan type for account")
            }
        }
    }
}