/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.microblink.core.ScanResults
import com.microblink.digital.BlinkReceiptDigitalSdk
import com.microblink.digital.GmailAuthException
import com.microblink.digital.GmailClient
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
    private lateinit var imapClient: ImapClient
    private var gmailClient: GmailClient? = null

    /**
     * Initializes [BlinkReceiptDigitalSdk] and instantiates [imapClient] and [gmailClient].
     *
     * @param req Data received from the Capacitor plugin.
     * @param context App [Context].
     * @param onError Callback called when an error occurs.
     * @return A [CompletableDeferred] to indicate when the initialization is complete.
     */
    suspend fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject?) -> Unit,
    ): CompletableDeferred<Unit> {
        val isDigitalInitialized = CompletableDeferred<Unit>()
        BlinkReceiptDigitalSdk.productIntelligenceKey(req.productKey)
        BlinkReceiptDigitalSdk.initialize(
            context,
            req.licenseKey,
            OnInitialize(isDigitalInitialized, onError)
        )
        isDigitalInitialized.await()
        if (req.googleId != null) {
            try {
                gmailClient = GmailClient(context, req.googleId)
            } catch (err: Exception) {
                onError(err.message, null)
            }
        }
        val isImapInitialized = CompletableDeferred<Unit>()
        imapClient =
            ImapClient(context, OnInitialize(isImapInitialized, onError)).apply { dayCutoff(30) }
        return isImapInitialized
    }

    /**
     * Logs in to the email provider using [ImapClient].
     *
     * @param call Plugin call.
     * @param account The email account information.
     * @param activity The [AppCompatActivity] where the login dialog will be displayed.
     */
    fun login(call: PluginCall, activity: AppCompatActivity, account: Account) {
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
                ProviderSetupResults.CREATED_APP_PASSWORD -> call.reject("CREATED_APP_PASSWORD")
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
                         account.isVerified =imapClient.verify(PasswordCredentials.newBuilder(
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
     * Logs in to the email provider using [GmailClient].
     * Use this if you want to log in with google sing in.
     *
     * @param call Plugin call.
     * @param activity The [AppCompatActivity] where the login dialog will be displayed.
     * @param gmailLoginCallback callback that is executed when the google activity is closed
     */
    fun login(call: PluginCall, activity: Activity, gmailLoginCallback: (Intent, Int) -> Unit) {
        if (gmailClient != null) {
            gmailClient!!.login()
                .addOnSuccessListener { gmailAccount ->
                    call.resolve(Account.fromGmailAccount(gmailAccount).toRsp())
                }.addOnFailureListener { error ->
                    if (error is GmailAuthException) {
                        gmailLoginCallback(error.signInIntent!!, error.requestCode)
                    } else {
                        call.reject(error.message)
                    }
                }
        } else {
            call.reject("Please define a googleId before the login")
        }
    }

    /**
     * Handles the result of the Gmail account authorization activity.
     *
     * @param call Plugin call.
     * @param requestCode The request code passed to the activity.
     * @param resultCode The result code indicating the result of the activity.
     * @param data The data returned from the activity.
     */
    fun onLoginResult(call: PluginCall, requestCode: Int, resultCode: Int, data: Intent?) {
        gmailClient!!
            .onAccountAuthorizationActivityResult(requestCode, resultCode, data)
            .addOnSuccessListener { signInAccount ->
                call.resolve(Account.fromGmailAccount(signInAccount).toRsp())
            }.addOnFailureListener {error ->
                call.reject(error.message)
            }
    }

    /**
     * Scrapes emails from both IMAP and Gmail providers.
     *
     * @param call Plugin call.
     * @param activity The calling activity.
     */
    fun scrape(call: PluginCall, activity: Activity) {
        val imap = {
            imapClient.messages(object : MessagesCallback {
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
        }
        val gmail = {account: Account ->
            gmailClient!!.messages(activity)
                .addOnSuccessListener { result ->
                    MainScope().async {
                        result.forEach { receipt ->
                            val rsp = RspScan(receipt, account)
                            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                        }
                    }
                }.addOnFailureListener { e ->
                    call.reject(e.message)
                }
        }
        MainScope().async {
        if (gmailClient != null){
            val accountGmail = accounts().await()
                .firstOrNull { it.accountCommon.source == EmailEnum.GMAIL.toString() }
            if (accountGmail != null) {
                gmail(accountGmail)
                imap()
            } else {
                imap()
            }
        } else {
            imap()
        }

        }
    }

    /**
     * Scrapes emails for a specific account from either IMAP or Gmail.
     *
     * @param call Plugin call.
     * @param account The email account information.
     * @param activity The calling activity.
     */
    fun scrape(call: PluginCall, account: Account, activity: Activity) {
        if (account.accountCommon.source == EmailEnum.GMAIL.toString() && gmailClient != null){
            gmailClient!!.messages(activity)
                .addOnSuccessListener { result ->
                    MainScope().async {
                        result.forEach { receipt ->
                            val rsp = RspScan(receipt, account)
                            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                        }
                    }
                }.addOnFailureListener { e ->
                    call.reject(e.message)
                }
        }else{
            imapClient.messages(object : MessagesCallback {
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
        }

    }

    /**
     * Retrieves a list of email accounts.
     *
     * @return A [CompletableDeferred] containing a list of email accounts.
     */
    suspend fun accounts(): CompletableDeferred<List<Account>> {
        val accounts = CompletableDeferred<List<Account>>()
        val accountList = mutableListOf<Account>()

        if (gmailClient != null) {
            val getGmail = CompletableDeferred<Unit>()
            gmailClient!!.account().addOnSuccessListener {
                MainScope().async {
                    val account = Account.fromGmailAccount(it)
                    account.isVerified = gmailClient!!.verify().await()
                    accountList.add(account)
                    getGmail.complete(Unit)
                }
            }.addOnFailureListener {
                getGmail.complete(Unit)
            }
            getGmail.await()
        }

        imapClient.accounts().addOnSuccessListener { credentials ->
            if (credentials != null) {
                MainScope().async {
                    credentials.forEach { credential ->
                        val account = Account.fromEmailAccount(credential)
                        if (account.accountCommon.source == EmailEnum.GMAIL.toString()) {
                            if (gmailClient == null) {
                                account.isVerified = imapClient.verify(credential).await()
                                accountList.add(account)
                            }
                        } else {
                            account.isVerified = imapClient.verify(credential).await()
                            accountList.add(account)
                        }
                    }
                    accounts.complete(accountList)
                }
            } else {
                accounts.complete(accountList)
            }
        }.addOnFailureListener {
            accounts.complete(accountList)
        }
        return accounts
    }

    /**
     * Removes an email account.
     *
     * This function allows the removal of an email account. If the account is a Gmail account,
     * it will be logged out from Gmail. For other email providers, it will be logged out from
     * the IMAP server.
     *
     * @param call Plugin call.
     * @param account The email account information to be removed.
     */
    fun remove(call: PluginCall, account: Account) {
        if (account.accountCommon.source == EmailEnum.GMAIL.toString() && gmailClient != null){
            gmailClient!!.logout().addOnSuccessListener {
                call.resolve(JSObject().put("success", it))
            }.addOnFailureListener {
                call.reject(it.message)
            }
        } else {
            imapClient.logout(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(account.accountCommon.source),
                    account.username,
                    account.password!!
                ).build()
            ).addOnSuccessListener {
                call.resolve(JSObject().put("success", it))
            }.addOnFailureListener {
                call.reject(it.message)
            }
        }
    }

    /**
     * Logs out of all email accounts.
     *
     * @param call Plugin call.
     */
    fun flush(call: PluginCall) {
        val imap = {
            imapClient.logout().addOnSuccessListener {
                call.resolve(JSObject().put("success", it))
            }.addOnFailureListener {
                call.reject(it.message)
            }
        }
        if (gmailClient != null) {
            gmailClient!!.logout().addOnSuccessListener {
                imap()
            }.addOnFailureListener {
                call.reject(it.message)
            }
        } else {
            imap()
        }

    }
}
