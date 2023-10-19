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
import java.util.Calendar
import kotlin.math.floor

typealias OnReceiptCallback = ((receipt: ScanResults?) -> Unit)

/**
 * Class that handles all email-related logic.
 *
 * @constructor Creates an empty Email.
 */
class Email {
    private val tag = "ProviderSetupDialogFragment"
    /**
     * Initializes [BlinkReceiptDigitalSdk] and instantiates [imapClient].
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
     * Scrapes receipts from the email using [ImapClient].
     *
     * @param context The application context.
     * @param onReceipt Callback called for each collected receipt.
     * @param onError Callback called when an error occurs during scraping.
     * @param onComplete Callback called when scrap is com=
     */
    fun scrape(
        context: Context,
        onReceipt: (receipt: ScanResults?) -> Unit,
        onError: (msg: String) -> Unit,
        onComplete: () -> Unit
    ) {
        MainScope().async {
            var dayCutOff = 15
            val now = Calendar.getInstance().timeInMillis
            val lastScrape = context.getImapScanTime().await()
            val diffInMillis = now - lastScrape
            val diffInDays = floor((diffInMillis / 86400000).toDouble()).toInt()
            if (diffInDays <= 15) {
                dayCutOff = diffInDays
            }
            this@Email.client(context, onError) { client ->
                client.accounts().addOnSuccessListener { credentials ->
                   if (credentials.isNullOrEmpty()) {
                       onComplete()
                   }else{
                       client.dayCutoff(dayCutOff)
                       client.messages(object : MessagesCallback {
                           override fun onComplete(
                               credential: PasswordCredentials,
                               result: List<ScanResults>
                           ) {
                               result.forEach { receipt ->
                                   onReceipt(receipt)
                               }
                               context.setImapScanTime(now)
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
                }.addOnFailureListener {
                    onComplete()
                }
            }
        }
    }

    /**
     * Retrieves a list of email accounts logged using [ImapClient].
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
                if (credentials.isNullOrEmpty()) {
                    onComplete?.invoke()
                    client.close()
                } else {
                    MainScope().async {
                        var returnedAccounts = 0
                        for (credential in credentials) {
                            val account = Account.fromEmailAccount(credential)
                            account.isVerified = true
                            onAccount(account)
                            returnedAccounts++
                            if (returnedAccounts == credentials.size) {
                                onComplete?.invoke()
                                client.close()
                            }
                        }

                    }
                }
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
                onComplete?.invoke()
                client.close()
            }
        }
    }

    /**
     * Removes an email account.
     *
     * This function allows the removal of an specific email account in [ImapClient].
     *
     * @param context The application context.
     * @param account The email account information to be removed.
     * @param onRemove Callback called when the account is successfully removed.
     * @param onError Callback called when an error occurs during account removal.
     */
    fun logout(
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
                client.clearLastCheckedTime(Provider.valueOf(account.accountCommon.id))
                context.deleteImapScanTime()
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
            client.clearLastCheckedTime()
            context.deleteImapScanTime()
            client.logout().addOnSuccessListener {
                onComplete()
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
                onComplete()
            }
        }
    }

    fun client(
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
