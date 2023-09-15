/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqScan
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspInitialized
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

/**
 * A plugin for capturing and processing receipts in a Capacitor-based Android application.
 */
class ReceiptCapture {
    val physical = Physical()
    val email = Email()
    val retailer = Retailer()

    /**
     * Initialize the receipt capture plugin.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun initialize(call: PluginCall, activity: AppCompatActivity) {
        val req = ReqInitialize(call.data)
        MainScope().async {
            physical.initialize(req, activity) { msg, data -> call.reject(msg, data) }.await()
            email.initialize(req, activity) { msg, data -> call.reject(msg, data) }.await()
            retailer.initialize(req, activity) { msg, data -> call.reject(msg, data) }.await()
            val rsp = RspInitialized(true)
            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
        }
    }

    /**
     * Login with the specified account.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun login(call: PluginCall, activity: AppCompatActivity, loginCallback: (Intent, Int) -> Unit) {
        val source = call.data.getString("source")
        val username = call.data.getString("username")
        val password = call.data.getString("password")
        if (source.isNullOrEmpty()) {
            call.reject("Provide source in login request")
        } else {
            if(source == EmailEnum.GMAIL.toString()){
                email.login(call, activity, loginCallback)
            } else {
                if (username.isNullOrEmpty()) {
                    call.reject("Provide username in login request")
                }
                if (password.isNullOrEmpty()) {
                    call.reject("Provide password in login request")
                }

                val account = Account.fromReq(call.data)
                when (account.accountCommon.type) {
                    AccountTypeEnum.EMAIL -> email.login(call, activity, account)
                    AccountTypeEnum.RETAILER -> retailer.login(call, account, activity)
                }
            }
        }
    }

    /**
     * Logout from the specified account or all accounts.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun logout(call: PluginCall, activity: AppCompatActivity) {
        val source = call.data.getString("source")
        val username = call.data.getString("username")
        val password = call.data.getString("password")
        if(source.isNullOrEmpty() && username.isNullOrEmpty()){
            retailer.flush(call)
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
                    retailer.remove(call, account.accountCommon, activity)
                }
            }
        } else if(source.isNullOrEmpty() && !username.isNullOrEmpty()){
            call.reject("Provide source in logout request.")
        } else if(!source.isNullOrEmpty() && username.isNullOrEmpty()) {
            val accountCommon = AccountCommon.fromString(source)
            if (accountCommon.type == AccountTypeEnum.RETAILER) {
                retailer.remove(call, accountCommon, activity)
            } else {
                call.reject("Provide username in email logout request.")
            }
        }
    }

    /**
     * Get a list of accounts.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun accounts(call: PluginCall, activity: AppCompatActivity) {
        MainScope().async {
            val list = mutableListOf<Account>()
            val emails = email.accounts().await()
            val retailers = retailer.accounts(activity).await()
            emails.forEach {list.add(it)}
            retailers.forEach {list.add(it)}
            call.resolve(Account.toRspList(list))
        }
    }

    /**
     * Perform a receipt scan operation.
     *
     * @param plugin The Capacitor plugin.
     * @param call The plugin call object.
     * @param activity The Android application activity.
     * @param reqPermCallback Callback function for requesting permissions.
     */
    fun scan(plugin: Plugin, call: PluginCall, activity: AppCompatActivity, reqPermCallback: () -> Unit) {
        val req = ReqScan(call.data)
        if(req.account == null) {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> email.scrape(call, activity)
                ScanTypeEnum.RETAILER -> retailer.orders(call, activity)
                ScanTypeEnum.PHYSICAL -> physical.scan(call, plugin, activity, reqPermCallback)
                ScanTypeEnum.ONLINE -> {
                    email.scrape(call, activity)
                    retailer.orders(call, activity)
                }
            }
        } else {
            when (req.scanType) {
                ScanTypeEnum.EMAIL -> email.scrape(call, req.account, activity)
                ScanTypeEnum.RETAILER -> retailer.orders(call, req.account, activity)
                else -> call.reject("invalid scan type for account")
            }
        }
    }
}
