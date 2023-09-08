/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.core.ScanResults
import com.microblink.core.Timberland
import com.microblink.digital.BlinkReceiptDigitalSdk
import com.microblink.digital.ImapClient
import com.microblink.digital.MessagesCallback
import com.microblink.digital.PasswordCredentials
import com.microblink.digital.Provider
import com.microblink.digital.ProviderSetupDialogFragment
import com.microblink.digital.ProviderSetupOptions
import com.microblink.digital.ProviderSetupResults
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqInitialize
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspScan
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

/**
 * Class that handles all email-related logic.
 *
 * @constructor Creates an empty Email.
 */
class Email {
    private val tag = "ProviderSetupDialogFragment"
    private lateinit var client: ImapClient

    /**
     * Initializes [BlinkReceiptDigitalSdk] and instantiates [client].
     *
     * @param req Data received from the Capacitor plugin.
     * @param context App [Context].
     * @param onError Callback called when an error occurs.
     * @return A [CompletableDeferred] to indicate when the initialization is complete.
     */
    suspend fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isDigitalInitialized = CompletableDeferred<Unit>()
        BlinkReceiptDigitalSdk.productIntelligenceKey(req.productKey)
        BlinkReceiptDigitalSdk.initialize(
            context,
            req.licenseKey,
            OnInitialize(isDigitalInitialized, onError)
        )
        isDigitalInitialized.await()
        val isImapInitialized = CompletableDeferred<Unit>()
        client =
            ImapClient(context, OnInitialize(isImapInitialized, onError)).apply { dayCutoff(30) }
        return isImapInitialized
    }

    /**
     * Logs in to the email provider.
     *
     * @param call Plugin call.
     * @param account The email account information.
     * @param activity The [AppCompatActivity] where the login dialog will be displayed.
     */
    fun login(call: PluginCall, account: Account, activity: AppCompatActivity) {
        ProviderSetupDialogFragment.newInstance(
            ProviderSetupOptions.newBuilder(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(account.accountCommon.source),
                    account.username,
                    account.password!!
                ).build()
            ).build()
        ).callback {
            when (it) {
                ProviderSetupResults.BAD_PASSWORD -> call.reject("Bad Password")
                ProviderSetupResults.BAD_EMAIL -> call.reject("Bad Email")
                ProviderSetupResults.CREATED_APP_PASSWORD -> Timberland.d("CREATED_APP_PASSWORD")
                ProviderSetupResults.NO_CREDENTIALS -> call.reject("No Credentials")
                ProviderSetupResults.UNKNOWN -> call.reject("Unknown")
                ProviderSetupResults.NO_APP_PASSWORD -> call.reject("No App Password")
                ProviderSetupResults.LSA_ENABLED -> call.reject("LSA Enabled")
                ProviderSetupResults.DUPLICATE_EMAIL -> call.reject("Duplicate Email")
            }
            if (!activity.supportFragmentManager.isDestroyed) {
                val dialog = activity.supportFragmentManager.findFragmentByTag(tag)
                        as ProviderSetupDialogFragment
                if (dialog.isAdded) {
                    dialog.dismiss()
                    MainScope().async {
                         account.isVerified =client.verify(PasswordCredentials.newBuilder(
                            Provider.valueOf(account.accountCommon.source),
                            account.username,
                            account.password
                        ).build()
                        ).await()
                        call.resolve(account.toRsp())
                    }
                }
            }
        }.show(activity.supportFragmentManager, tag)
    }

    /**
     * Scrapes emails.
     *
     * @param call Plugin call.
     */
    fun scrape(call: PluginCall) {
        call.reject("Not initialized")
        call.setKeepAlive(true)
        client.messages(object : MessagesCallback {
            override fun onComplete(
                credential: PasswordCredentials,
                result: List<ScanResults>
            ) {
                result.forEach { receipt ->
                    val rsp = RspScan(receipt, Account.fromEmailAccount(credential))
                    call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                }
            }
            override fun onException(throwable: Throwable) = call.reject(throwable.message)
        })
        call.setKeepAlive(false)
    }

    /**
     * Scrapes emails for a specific account.
     *
     * @param call Plugin call.
     * @param account The email account information.
     */
    fun scrape(call: PluginCall, account: Account) {
        call.reject("Not initialized")
        call.setKeepAlive(true)
        client.messages(object : MessagesCallback {
            override fun onComplete(
                credential: PasswordCredentials,
                result: List<ScanResults>
            ) {
                if (credential.provider() === EmailEnum.fromString(account.accountCommon.source).value) {
                    result.forEach { receipt ->
                        val rsp = RspScan(receipt, Account.fromEmailAccount(credential))
                        call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                    }
                }
            }

            override fun onException(throwable: Throwable) = call.reject(throwable.message)
        })
        call.setKeepAlive(false)
    }

    /**
     * Retrieves a list of email accounts.
     *
     * @return A [CompletableDeferred] containing a list of email accounts.
     */
    fun accounts(): CompletableDeferred<List<Account>> {
        val isAccounts = CompletableDeferred<List<Account>>()
        client.accounts()?.addOnSuccessListener { credentials ->
            if (credentials != null) {
                MainScope().async {
                    val accountList = credentials?.map { credential ->
                        val account = Account.fromEmailAccount(credential)
                        account.isVerified = client.verify(credential).await()
                        account
                    }
                    isAccounts.complete(accountList!!)
                }
            } else {
                isAccounts.complete(mutableListOf())
            }
        }?.addOnFailureListener {
            isAccounts.completeExceptionally(it)
        }
        return isAccounts
    }

    /**
     * Removes an email account.
     *
     * @param call Plugin call.
     * @param account The email account information.
     */
    fun remove(call: PluginCall, account: Account) {
        client.logout(
            PasswordCredentials.newBuilder(
                Provider.valueOf(account.accountCommon.source),
                account.username,
                account.password!!
            ).build()
        )?.addOnSuccessListener {
            call.resolve(JSObject().put("success", it))
        }?.addOnFailureListener {
            call.reject(it.message)
        }
    }

    /**
     * Logs out of all email accounts.
     *
     * @param call Plugin call.
     */
    fun flush(call: PluginCall) {
        client.logout()?.addOnSuccessListener {
            call.resolve()
        }?.addOnFailureListener {
            call.reject(it.message)
        }
    }
}
