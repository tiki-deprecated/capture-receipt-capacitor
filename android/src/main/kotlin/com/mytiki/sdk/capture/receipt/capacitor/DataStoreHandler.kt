package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.datastore.preferences.core.*
import com.mytiki.sdk.capture.receipt.capacitor.plugin.dataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

val key = stringPreferencesKey("tiki-captures-receipt.imap-latest-date")

/**
 * Store a [Long] value in the DataStore.
 *
 * @param value The [Long] value to store.
 */
fun Context.setDate(value: Long) {
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
fun Context.getDate(onComplete: (Long) -> Unit, onError: (String) -> Unit) {
    MainScope().async {
        val longValue = dataStore.data.map { pref ->
            pref[longPreferencesKey(key.name)] ?: 0L
        }
        longValue.distinctUntilChanged().collect { value ->
            if (value !== null) {
                onComplete(value)
            } else {
                setDate(0L)
            }
        }
    }
}

/**
 * Delete a [Long] value from the DataStore.
 */
fun Context.deleteDate(){
    // Use async to perform data removal asynchronously
    MainScope().async {
        dataStore.edit { pref -> pref.clear() }
    }
}

