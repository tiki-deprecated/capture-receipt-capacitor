/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import com.microblink.core.ScanResults
import com.microblink.linking.Account
import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum
import org.json.JSONArray
import org.json.JSONObject

class RspRetailerOrders( account: Account, order: ScanResults) : Rsp {
    private val login: RspRetailerAccount = RspRetailerAccount(account.credentials.username(), RetailerEnum.fromInt(account.retailerId))
    private val scan: RspScan = RspScan(order)

    override fun toJson(): JSONObject =
        JSONObject()
            .put("login", login.toJson())
            .put("scan",  scan.toJson())

}
