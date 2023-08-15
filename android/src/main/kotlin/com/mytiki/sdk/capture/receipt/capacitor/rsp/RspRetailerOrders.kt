/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import android.net.wifi.ScanResult
import com.microblink.core.ScanResults
import com.microblink.linking.Account
import com.mytiki.sdk.capture.receipt.capacitor.RetailerEnum
import org.json.JSONArray
import org.json.JSONObject

class RspRetailerOrders(
    private val retailerId: RetailerEnum,
    private val login: String,
    private val scan: ScanResults
): Rsp {
    override fun toJson(): JSONObject =
        JSONObject()
            .put("login", login)
            .put("retailerId", retailerId.value)
            .put("scan",  RspScan(scan))
}
