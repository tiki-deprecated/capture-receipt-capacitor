/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import android.os.Build
import androidx.annotation.RequiresApi
import com.mytiki.sdk.capture.receipt.capacitor.Account
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RspAccountList(
    private val accounts: MutableList<Account>,
    private val error: Exception? = null
    ) : Rsp {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun toJson(): JSONObject =
        JSONObject()
            .put("accounts", JSONArray(accounts.map { account -> account.toRsp() }))
            .put("error", JSONException(error))
}
