/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.microblink.core.ScanResults
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

typealias OnErrorCallback = ((message: String) -> Unit)
typealias OnAccountCallback = ((account: Account) -> Unit)
typealias OnReceiptCallback = ((receipt: ScanResults?) -> Unit)
typealias OnCompleteCallback = (() -> Unit)

/**
 * A plugin for capturing and processing receipts in a Capacitor-based Android application.
 */
class ReceiptCapture {
    private val email = Email()
    private val retailer = Retailer()

    /**
     * Initialize the receipt capture plugin.
     *
     * @param context The Android application context.
     * @param licenseKey The license key.
     * @param productKey The product key.
     */
    fun initialize(
        context: Context,
        licenseKey: String,
        productKey: String,
        onInitialize: OnCompleteCallback,
        onError: OnErrorCallback
    ) {
        val deferredEmailInit = email.initialize(context, licenseKey, productKey) { msg ->
            onError(msg ?: "Email initialization error")
        }
        val deferredRetailInit = retailer.initialize(context, licenseKey, productKey) { msg ->
            onError(msg)
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
     * @param id The source of the account (e.g., EmailEnum.GMAIL.toString() or an enum value from RetailerEnum).
     */
    fun login(
        activity: AppCompatActivity,
        username: String,
        password: String,
        id: String,
        onLogin: OnAccountCallback,
        onError: OnErrorCallback
    ) {
        if (id == EmailEnum.GMAIL.toString()) {
            email.login(
                username,
                password,
                id,
                activity.supportFragmentManager,
                onLogin,
                onError
            )
        } else {
            retailer.login(username, password, id, activity, onLogin, onError)
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
    fun logout(
        context: Context,
        account: Account?,
        onComplete: OnCompleteCallback,
        onError: OnErrorCallback
    ) {
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
    fun accounts(context: Context, onAccount: OnAccountCallback, onError: OnErrorCallback, onComplete: OnCompleteCallback) {
        val emailFinished = CompletableDeferred<Unit>()
        val retailerFinished = CompletableDeferred<Unit>()
        MainScope().async {
            email.accounts(context, onAccount, onError) { emailFinished.complete(Unit) }
            retailer.accounts(context, onAccount, onError) { retailerFinished.complete(Unit) }
            awaitAll(emailFinished, retailerFinished)
            onComplete()
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
    fun scan(
        context: Context,
        dayCutOff: Int? = null,
        onReceipt: OnReceiptCallback,
        onComplete: OnCompleteCallback,
        onError: OnErrorCallback
    ) {
        val emailFinished = CompletableDeferred<Unit>()
        val retailerFinished = CompletableDeferred<Unit>()
        email.scrape(context, onReceipt, onError, dayCutOff ?: 7) { emailFinished.complete(Unit) }
        retailer.orders(context, onReceipt, onError, dayCutOff ?: 7) { retailerFinished.complete(Unit) }
    }
}
