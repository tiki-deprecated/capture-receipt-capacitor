/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.google.android.gms.common.internal.AccountType
import com.mytiki.sdk.capture.receipt.capacitor.Account
import com.mytiki.sdk.capture.receipt.capacitor.AccountTypeEnum
import org.json.JSONObject

class RspAccount(
    private val account: Account
) : Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("username", account.username)
            .put("source", account.accountType.source)
            .put("type", account.accountType.type.name)
            .put("isVerified", account.isVerified)
}