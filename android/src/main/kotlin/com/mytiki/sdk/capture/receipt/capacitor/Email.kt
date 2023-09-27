/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.microblink.core.InitializeCallback
import com.microblink.core.ScanResults
import com.microblink.digital.BlinkReceiptDigitalSdk
import com.microblink.digital.ImapClient
import com.microblink.digital.MessagesCallback
import com.microblink.digital.PasswordCredentials
import com.microblink.digital.Provider
import com.microblink.digital.ProviderSetupDialogFragment
import com.microblink.digital.ProviderSetupOptions
import com.microblink.digital.ProviderSetupResults
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

    /**
     * Initializes [BlinkReceiptDigitalSdk] and instantiates [imapClient] and [gmailClient].
     *
     * @param req Data received from the Capacitor plugin.
     * @param context App [Context].
     * @param onError Callback called when an error occurs.
     * @return A [CompletableDeferred] to indicate when the initialization is complete.
     */
    fun initialize(
        context: Context,
        licenseKey: String,
        productKey: String,
        onError: (msg: String?) -> Unit,
    ): CompletableDeferred<Unit> {
        val isInitialized = CompletableDeferred<Unit>()
        BlinkReceiptDigitalSdk.productIntelligenceKey(productKey)
        BlinkReceiptDigitalSdk.initialize(
            context,
            licenseKey,
            OnInitialize(isInitialized, onError)
        )
        return isInitialized
    }

    /**
     * Logs in to the email provider using [ImapClient].
     *
     * @param call Plugin call.
     * @param account The email account information.
     * @param activity The [AppCompatActivity] where the login dialog will be displayed.
     */
    fun login(
        username: String,
        password: String,
        source: String,
        supportFragmentManager: FragmentManager,
        onComplete: ((Account) -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        ProviderSetupDialogFragment.newInstance(
            ProviderSetupOptions.newBuilder(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(source),
                    username,
                    password
                ).build()
            ).build()
        ).callback { results ->
            when (results) {
                ProviderSetupResults.CREATED_APP_PASSWORD -> {
                    val account = Account(
                        AccountCommon.fromSource(source), username, password, true
                    )
                    onComplete?.let { it(account) }
                }

                else -> {
                    onError?.let { it(it.toString()) }
                }
            }
            if (!supportFragmentManager.isDestroyed) {
                val dialog = supportFragmentManager.findFragmentByTag(tag)
                        as ProviderSetupDialogFragment
                if (dialog.isAdded) {
                    dialog.dismiss()
                }
            }
        }.show(supportFragmentManager, tag)
    }

    /**
     * Scrapes emails from both IMAP and Gmail providers.
     *
     * @param call Plugin call.
     * @param activity The calling activity.
     */
    fun scrape(
        context: Context,
        onReceipt: (receipt: ScanResults?) -> Unit,
        onError: (msg: String) -> Unit,
        dayCutOff: Int
    ) {
        this.client(context, onError) { client ->
            client.dayCutoff(dayCutOff)
            client.messages(object : MessagesCallback {
                override fun onComplete(
                    credential: PasswordCredentials,
                    result: List<ScanResults>
                ) {
                    result.forEach { receipt ->
                        onReceipt(receipt)
                    }
                    client.close()
                }

                override fun onException(throwable: Throwable) {
                    onError(throwable.message ?: throwable.toString())
                    client.close()
                }
            })
        }
    }

    /**
     * Retrieves a list of email accounts.
     *
     * @return A [CompletableDeferred] containing a list of email accounts.
     */
    fun accounts(context: Context, onAccount: (Account) -> Unit, onError: (msg: String) -> Unit) {
        this.client(context, onError) { client ->
            client.accounts().addOnSuccessListener { credentials ->
                credentials?.forEach { credential ->
                    val account = Account.fromEmailAccount(credential)
                    MainScope().async {
                        account.isVerified = client.verify(credential).await()
                        onAccount(account)
                    }
                }
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
            }
        }
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
    fun remove(
        context: Context,
        account: Account,
        onRemove: () -> Unit,
        onError: (String) -> Unit
    ) {
        this.client(context, onError) { client ->
            client.logout(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(account.accountCommon.source),
                    account.username,
                    account.password!!
                ).build()
            ).addOnSuccessListener {
                onRemove()
            }.addOnFailureListener {
                onError(
                    it.message
                        ?: "Unknown error when removing account ${account.username} from ${account.accountCommon.source}"
                )
            }
        }
    }

    /**
     * Logs out of all email accounts.
     *
     * @param call Plugin call.
     */
    fun flush(context: Context, onComplete: () -> Unit, onError: (msg: String) -> Unit) {
        this.client(context, onError) { client ->
            client.logout().addOnSuccessListener {
                onComplete()
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
            }
        }
    }

    private fun client(
        context: Context, onError: (String) -> Unit,
        onClientReady: (ImapClient) -> Unit
    ) {
        val clientInitialization = CompletableDeferred<Unit>()
        val imapClient = ImapClient(
            context,
            object : InitializeCallback {
                override fun onComplete() {
                    clientInitialization.complete(Unit)
                }

                override fun onException(ex: Throwable) {
                    onError(ex.message ?: "Error in IMAP client initializaion: $ex")
                }
            }
        )
        MainScope().async {
            clientInitialization.await()
            onClientReady(imapClient)
        }
    }
}
