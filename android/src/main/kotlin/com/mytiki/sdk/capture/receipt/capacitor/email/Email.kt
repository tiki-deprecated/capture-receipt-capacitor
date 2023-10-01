/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.email

import android.content.Context
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
import com.mytiki.sdk.capture.receipt.capacitor.OnAccountCallback
import com.mytiki.sdk.capture.receipt.capacitor.OnCompleteCallback
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

typealias OnReceiptCallback = ((receipt: ScanResults?) -> Unit)

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
     * @param context The application context.
     * @param licenseKey The license key.
     * @param productKey The product key.
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
            object : InitializeCallback {
                override fun onComplete() {
                    isInitialized.complete(Unit)
                }

                override fun onException(ex: Throwable) {
                    onError(ex.message)
                }
            }
        )
        return isInitialized
    }

    /**
     * Logs in to the email provider using [ImapClient].
     *
     * @param username The username.
     * @param password The password.
     * @param id The id (email provider).
     * @param supportFragmentManager The fragment manager.
     * @param onComplete Callback called when the login is completed successfully.
     * @param onError Callback called when an error occurs during login.
     */
    fun login(
        username: String,
        password: String,
        id: String,
        supportFragmentManager: FragmentManager,
        onComplete: ((Account) -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        ProviderSetupDialogFragment.newInstance(
            ProviderSetupOptions.newBuilder(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(id),
                    username,
                    password
                ).build()
            ).build()
        ).callback { results ->
            when (results) {
                ProviderSetupResults.CREATED_APP_PASSWORD -> {
                    val account = Account(
                        AccountCommon.fromSource(id), username, password, true
                    )
                    onComplete?.invoke(account)
                }

                else -> {
                    onError?.invoke(results.toString())
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
     * @param context The application context.
     * @param onReceipt Callback called for each collected receipt.
     * @param onError Callback called when an error occurs during scraping.
     * @param dayCutOff The day cutoff limit for scraping.
     */
    fun scrape(
        context: Context,
        onReceipt: (receipt: ScanResults?) -> Unit,
        onError: (msg: String) -> Unit,
        dayCutOff: Int,
        onComplete: () -> Unit
    ) {
        this.client(context, onError) { client ->
            client.dayCutoff(dayCutOff)
            client.messages(object : MessagesCallback {
                override fun onComplete(
                    credential: PasswordCredentials,
                    result: List<ScanResults>
                ) {
                    if (result.isEmpty()) {
                        onError("No results for ${credential.username()} - ${credential.provider()}")
                    }
                    result.forEach { receipt ->
                        onReceipt(receipt)
                    }
                    onComplete()
                    client.close()
                }

                override fun onException(throwable: Throwable) {
                    onError(throwable.message ?: throwable.toString())
                    onComplete()
                    client.close()
                }
            })
        }
    }

    /**
     * Retrieves a list of email accounts.
     *
     * @param context The application context.
     * @param onAccount Callback called for each retrieved email account.
     * @param onError Callback called when an error occurs during account retrieval.
     */
    fun accounts(
        context: Context,
        onAccount: OnAccountCallback,
        onError: ((msg: String) -> Unit),
        onComplete: OnCompleteCallback?
    ) {
        this.client(context, onError) { client ->
            client.accounts().addOnSuccessListener { credentials ->
                MainScope().async {
                    var returnedAccounts = 0
                    if (credentials != null) {
                        for (credential in credentials) {
                            val account = Account.fromEmailAccount(credential)

                            account.isVerified = client.verify(credential).await()
                            onAccount(account)
                            returnedAccounts++
                            if (returnedAccounts == credentials.size) {
                                onComplete?.invoke()
                            }
                        }
                    } else {
                        onError("Error in retrieving accounts. Account list is null.")
                        onComplete?.invoke()
                    }
                }
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
                onComplete?.invoke()
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
     * @param context The application context.
     * @param account The email account information to be removed.
     * @param onRemove Callback called when the account is successfully removed.
     * @param onError Callback called when an error occurs during account removal.
     */
    fun remove(
        context: Context,
        account: Account,
        onRemove: () -> Unit,
        onError: (String) -> Unit
    ) {
        this.client(context, onError) { client ->
            client.accounts().addOnSuccessListener { list ->
                val passwordCredentials = list.first {
                    it.username() == account.username && it.provider() == EmailEnum.fromString(
                        account.accountCommon.id
                    ).value
                }
                client.logout(passwordCredentials).addOnSuccessListener {
                    onRemove()
                }.addOnFailureListener {
                    onError(
                        it.message
                            ?: "Unknown error when removing account ${account.username} from ${account.accountCommon.id}"
                    )
                }
            }

        }
    }

    /**
     * Logs out of all email accounts.
     *
     * @param context The application context.
     * @param onComplete Callback called when the logout is completed successfully.
     * @param onError Callback called when an error occurs during logout.
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
                    onError(ex.message ?: "Error in IMAP client initialization: $ex")
                }
            }
        )
        MainScope().async {
            clientInitialization.await()
            onClientReady(imapClient)
        }
    }
}
