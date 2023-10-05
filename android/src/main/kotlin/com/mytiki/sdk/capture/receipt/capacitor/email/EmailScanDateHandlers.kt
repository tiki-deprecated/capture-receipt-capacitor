package com.mytiki.sdk.capture.receipt.capacitor.email

import android.content.Context
import androidx.datastore.preferences.core.*
import com.microblink.core.Timberland
import com.mytiki.sdk.capture.receipt.capacitor.plugin.dataStore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val key = stringPreferencesKey("tiki-captures-receipt.imap-latest-date")

/**
 * Store the scan time in milliseconds since epoch in DataStore preferences.
 *
 * @param value The [Long] value to store.
 */
fun Context.setImapScanTime(value: Long) {
    MainScope().async {
        dataStore.edit { pref -> pref[longPreferencesKey(key.name)] = value }
    }
}

/**
 * Retrieve a [Long] value from the DataStore preferences.
 *
 * @param onComplete Callback function to handle the retrieved [Long] value.
 * @param onError Callback function to handle errors.
 */
fun Context.getImapScanTime(): Deferred<Long> {
    return MainScope().async {
        try{
            //dataStore.data.first()[longPreferencesKey(key.name)] ?: 0L
            15

        }catch(ex: Exception){
           Timberland.d(ex.message ?: "Error in getting Imap scan time.")
            15
        }
    }
}

/**
 * Delete a [Long] value from the DataStore.
 */
fun Context.deleteImapScanTime(){
    MainScope().async {
        dataStore.edit { pref -> pref[longPreferencesKey(key.name)] = 0L }
    }
}

