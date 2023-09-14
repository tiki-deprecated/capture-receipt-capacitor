package com.example.poccapturesreceiptcapacitor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.microblink.core.InitializeCallback
import com.microblink.digital.BlinkReceiptDigitalSdk
import com.microblink.digital.GmailAuthException
import com.microblink.digital.GmailClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class GoogleSignIn {
    private lateinit var gmailClient: GmailClient
    val licenceKey =  "sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/jLI1A3BmOhqNvtZLzShMcb3/OLQLiqgWjuHuFiqGfg4fnAiPtRcc5uRJ6bCBRkg8EsKabMQkEsMOuVjvEOejVD497WkMgobMbk/X+bdfhPPGdcAHWn5Vnz86SmGdHX5xs6RgYe5jmJCSLiPmB7cjWmxY5ihkCG12Q=="
    val productKey = "wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw=="

    fun Initializer(context: Context, status: MutableState<String>, error: MutableState<String>, resp: MutableState<Int>, googleId: String) {
        BlinkReceiptDigitalSdk.productIntelligenceKey(productKey)
        BlinkReceiptDigitalSdk.initialize(context, licenceKey, object : InitializeCallback {
            override fun onComplete() {
                status.value = "Inicializou esse carai"
                resp.value = 1

            }

            override fun onException(e: Throwable) {
                status.value = "Deu merda esse carai"
                resp.value = 2
                error.value = e.message.toString()
            }
        })
        gmailClient = GmailClient(context, googleId)
    }

    fun login(activity: Activity, status: MutableState<String>, error: MutableState<String>, resp: MutableState<Int>) {
        gmailClient.login()
            .addOnSuccessListener {
                status.value = "Logou esse carai"
                resp.value = 1
            }.addOnFailureListener { e ->

                if (e is GmailAuthException) {
                    startActivityForResult(activity,e.signInIntent!!, e.requestCode, null)
                    error.value = "GmailAuthException: " + e.message.toString()
                } else {
                    status.value = "Deu merda no Login esse carai"
                    resp.value = 2
                    error.value = e.message.toString()
                }
            }
    }

    fun verifyUser(activity: Activity, status: MutableState<String>, error: MutableState<String>, resp: MutableState<Int>, account: MutableState<String>) {
        gmailClient.verify()
            .addOnSuccessListener {
                if (it) {
                    status.value = "Verificou esse carai"
                    resp.value = 1
                    MainScope().async {
                        account.value = gmailClient.account().await().email?: "deu merda"
                    }
                }else{
                    status.value = "Deu merda no verifyUser esse carai"
                    resp.value = 2
                }
            }.addOnFailureListener { e ->
                status.value = "Deu merda no verifyUser esse carai"
                resp.value = 2
                error.value = e.message.toString()
            }
    }

    fun logoutUser(activity: Activity, status: MutableState<String>, error: MutableState<String>, resp: MutableState<Int>) {
        gmailClient.logout()
            .addOnSuccessListener {
                status.value = "deslogou desse carai"
                resp.value = 1
            }.addOnFailureListener {e ->
                status.value = "Deu merda no logout esse carai"
                resp.value = 2
                error.value = e.message.toString()
            }
    }

    fun scan(activity: Activity, status: MutableState<String>, error: MutableState<String>, resp: MutableState<Int>, receipt: MutableState<String>) {
        gmailClient.messages(activity)
            .addOnSuccessListener { results ->
                status.value = "pegou o scan desse carai"
                resp.value = 1
                receipt.value = results[0].barcode().toString()

            }.addOnFailureListener {e ->
                status.value = "Deu merda no scan esse carai"
                resp.value = 2
                error.value = e.message.toString()
            }
    }

}