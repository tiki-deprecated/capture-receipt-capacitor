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
    val onError: ((message: String) -> Unit),
) {
    private val email = Email()
    private val retailer = Retailer()

    /**
     * Initialize the receipt capture plugin.
     *
     * @param context The Android application context.
     * @param licenseKey The license key.
     * @param productKey The product key.
     */
    fun initialize(context: Context, licenseKey: String, productKey: String) {
        val deferredEmailInit = email.initialize(context, licenseKey, productKey) { msg ->
            onError(msg ?: "Email initialization error")
        }
        val deferredRetailInit = retailer.initialize(context, licenseKey, productKey) { msg ->
            onError(msg ?: "Retailer initialization error")
        }
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
     * @param activity The Android application activity.
     * @param username The username for login.
     * @param password The password for login.
     * @param source The source of the account (e.g., EmailEnum.GMAIL.toString() or an enum value from RetailerEnum).
     */
    fun login(
        activity: AppCompatActivity,
        username: String,
        password: String,
        source: String
    ) {
        if (source == EmailEnum.GMAIL.toString()) {
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
     * @param context The Android application context.
     * @param account The account to log out (null for all accounts).
     * @param onComplete Callback function to execute after logout.
     */
    fun logout(context: Context, account: Account?, onComplete: () -> Unit) {
        if (account == null) {
            retailer.flush(context, onComplete, onError)
            email.flush(context, onComplete, onError)
        } else {
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
     * Get a list of accounts of the specified type.
     *
     * This function retrieves a list of email or retailer accounts.
     *
     * @param context The Android application context.
     * @param accountType The type of accounts to retrieve (AccountTypeEnum.EMAIL or AccountTypeEnum.RETAILER).
     */
    fun accounts(context: Context, accountType: AccountTypeEnum) {
        when (accountType) {
            AccountTypeEnum.EMAIL -> email.accounts(context, onAccount, onError)
            AccountTypeEnum.RETAILER -> retailer.accounts(context, onAccount, onError)
        }
    }

    /**
     * Perform a receipt scan operation.
     *
     * This function performs a receipt scan operation based on the provided day cutoff.
     *
     * @param context The Android application context.
     * @param dayCutOff The day cutoff for scanning receipts (default is 7 days).
     */
    fun scan(context: Context, dayCutOff: Int? = null) {
        email.scrape(context, onReceipt, onError, dayCutOff ?: 7)
        retailer.orders(context, onReceipt, onError, dayCutOff ?: 7)
    }
}
