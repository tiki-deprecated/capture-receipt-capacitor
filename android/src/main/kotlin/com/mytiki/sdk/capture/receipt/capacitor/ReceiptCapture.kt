/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.microblink.core.ScanResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


/**
 * A plugin for capturing and processing receipts in a Capacitor-based Android application.
 */
class ReceiptCapture(
    val onInitialize: (() -> Unit),
    val onReceipt: ((receipt: ScanResults?) -> Unit),
    val onAccount: ((account: Account?) -> Unit),
    val onError: ((message: String?) -> Unit),
){
    private val email = Email()
    private val retailer = Retailer()

    /**
     * Initialize the receipt capture plugin.
     *
     * @param reqInitialize The plugin call object.
     * @param activity The Android application activity.
     */
    fun initialize(context: Context, licenseKey: String, productKey: String) {
        val deferredEmailInit = email.initialize(context, licenseKey, productKey){ msg -> onError(msg) }
        val deferredRetailInit = retailer.initialize(context, licenseKey, productKey){ msg -> onError(msg) }
        MainScope().async {
            awaitAll(deferredEmailInit, deferredRetailInit)
            onInitialize()
        }
    }

    /**
     * Login with the specified account.
     *
     * This function allows users to log in with their email or retailer accounts.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     * @param gmailLoginCallback Callback function for handling login activities.
     */
    fun login(
        activity: AppCompatActivity,
        username: String,
        password: String,
        source: String
    ) {
        if(source == EmailEnum.GMAIL.toString()){
            email.login(username, password, source, activity.supportFragmentManager, onAccount, onError)
        } else {
            retailer.login(username, password, source, activity, onAccount, onError)
        }
    }

    /**
     * Logout from the specified account or all accounts.
     *
     * This function allows users to log out from specific accounts or all accounts.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun logout(context: Context, account: Account?, onComplete: () -> Unit) {
        if(account == null){
            retailer.flush(context, onComplete, onError)
            email.flush(context, onComplete, onError)
        } else{
            when (account.accountCommon.type) {
                AccountTypeEnum.EMAIL -> {
                    email.remove(context, account, onComplete, onError)
                }
                AccountTypeEnum.RETAILER -> {
                    retailer.remove(context, account, onComplete, onError)
                }
            }
        }
    }

    /**
     * Get a list of accounts.
     *
     * This function retrieves a list of email and retailer accounts.
     *
     * @param call The plugin call object.
     * @param activity The Android application activity.
     */
    fun accounts(context: Context, accountType: AccountTypeEnum) {
        when(accountType) {
            AccountTypeEnum.EMAIL -> email.accounts(context, onAccount, onError)
            AccountTypeEnum.RETAILER -> retailer.accounts(context, onAccount, onError)
        }
    }

    /**
     * Perform a receipt scan operation.
     *
     * This function performs a receipt scan operation based on the provided scan type and account.
     *
     * @param plugin The Capacitor plugin.
     * @param call The plugin call object.
     * @param activity The Android application activity.
     * @param reqPermCallback Callback function for requesting permissions.
     */
    fun scan(context: Context, dayCutOff: Int? = null) {
        email.scrape(context, onReceipt, onError, dayCutOff ?: 7)
        retailer.orders(context, onReceipt, onError, dayCutOff ?: 7)
    }
}
