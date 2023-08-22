/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
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
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqLogin
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspEmailAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspEmail
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspLogin
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject

class Email {
    private val tag = "ProviderSetupDialogFragment"
    private var client: ImapClient? = null

    suspend fun initialize(
        req: ReqInitialize,
        context: Context,
        onError: (msg: String?, data: JSObject) -> Unit,
    ): CompletableDeferred<Unit> {
        val isDigitalInitialized = CompletableDeferred<Unit>()
        BlinkReceiptDigitalSdk.productIntelligenceKey(req.productKey!!)
        BlinkReceiptDigitalSdk.initialize(
            context,
            req.licenseKey!!,
            OnInitialize(isDigitalInitialized, onError)
        )
        isDigitalInitialized.await()
        val isImapInitialized = CompletableDeferred<Unit>()
        client =
            ImapClient(context, OnInitialize(isImapInitialized, onError)).apply { dayCutoff(30) }
        return isImapInitialized
    }

    fun login(call: PluginCall, account: Account, activity: AppCompatActivity) {
        ProviderSetupDialogFragment.newInstance(
            ProviderSetupOptions.newBuilder(
                PasswordCredentials.newBuilder(
                    Provider.valueOf(account.accountType.source),
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
                ProviderSetupResults.UNKNOWN -> call.reject("unknown")
                ProviderSetupResults.NO_APP_PASSWORD -> call.reject("No App Password")
                ProviderSetupResults.LSA_ENABLED -> call.reject("LSA Enabled")
                ProviderSetupResults.DUPLICATE_EMAIL -> call.reject("Duplicate Email")
            }
            if (!activity.supportFragmentManager.isDestroyed) {
                val dialog = activity.supportFragmentManager.findFragmentByTag(tag)
                        as ProviderSetupDialogFragment
                if (dialog.isAdded) {
                    dialog.dismiss()
                    val rsp = RspLogin(account.username, account.accountType.source)
                    call.resolve(JSObject.fromJSONObject(rsp.toJson()))
                }
            }
        }.show(activity.supportFragmentManager, tag)
    }

    fun scrape(call: PluginCall) {
        if (client == null) call.reject("Not initialized")

        call.setKeepAlive(true)
        client!!.messages(object : MessagesCallback {
            override fun onComplete(
                credential: PasswordCredentials,
                result: List<ScanResults>
            ) {
                val rsp = RspEmail(credential, result)
                call.resolve(JSObject.fromJSONObject(rsp.toJson()))
            }

            override fun onException(throwable: Throwable) = call.reject(throwable.message)
        })
    }

    fun accounts(call: PluginCall) {
        val deferred = MainScope().async {
            val rsp: MutableList<JSONObject> = mutableListOf()
            val credentials = client?.accounts()?.await()
            credentials?.forEach { credential ->
                val verified = client?.verify(credential)?.await()
                rsp.add(
                    RspEmailAccount(
                        credential.username(),
                        credential.provider().name,
                        verified ?: false
                    ).toJson()
                )
            }
            call.resolve(JSObject.fromJSONObject(JSONObject().put("accounts", JSONArray(rsp))))
        }
    }

    fun remove(call: PluginCall, account: Account) {
        client?.logout(
            PasswordCredentials.newBuilder(
                Provider.valueOf(account.accountType.source),
                account.username,
                account.password!!
            ).build()
        )?.addOnSuccessListener {
            call.resolve(JSObject().put("success", it))
        }?.addOnFailureListener {
            call.reject(it.message)
        }
    }

    fun flush(call: PluginCall) {
        client?.logout()?.addOnSuccessListener {
            call.resolve()
        }?.addOnFailureListener {
            call.reject(it.message)
        }
    }
}