package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import androidx.datastore.preferences.core.*
import com.mytiki.sdk.capture.receipt.capacitor.plugin.dataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

const val key = "tiki-captures-receipt.imap-latest-date"
/**
 * Add a [String] value to the DataStore.
 *
 * @param key The key to associate with the [String] value.
 * @param value The [String] value to store.
 */
suspend fun Context.writeString( value: String) {
    dataStore.edit { pref -> pref[stringPreferencesKey(key)] = value }
}

/**
 * Read a [String] value from the DataStore preferences.
 *
 * @param key The key associated with the [String] value.
 * @return A Flow that emits the stored [String] value.
 */
fun Context.readString(): Flow<String> {
    return dataStore.data.map { pref ->
        pref[stringPreferencesKey(key)] ?: ""
    }
}

/**
 * Add a [Long] value to the DataStore.
 *
 * @param key The key to associate with the [Long] value.
 * @param value The [Long] value to store.
 */
fun Context.writeLong(value: Long) {
    MainScope().async {
        dataStore.edit { pref -> pref[longPreferencesKey(key)] = value }
    }
}

/**
 * Read a [Long] value from the DataStore preferences.
 *
 * @param key The key associated with the [Long] value.
 * @return A Flow that emits the stored [Long] value.
 */
fun Context.readLong(onComplete: (Long) -> Unit, onError: (String) -> Unit) {
    MainScope().async {
        val longValue = dataStore.data.map { pref ->
            pref[longPreferencesKey(key)] ?: 0L
        }
        longValue.distinctUntilChanged().collect { value ->
            if (value !== null) {
                onComplete(value)
            } else {
                onError("Value is null")

            }
        }
    }
}

