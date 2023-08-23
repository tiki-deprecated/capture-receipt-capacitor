package com.mytiki.sdk.capture.receipt.capacitor.req

import com.getcapacitor.JSObject
import com.mytiki.sdk.capture.receipt.capacitor.Account
import com.mytiki.sdk.capture.receipt.capacitor.AccountCommon
import com.mytiki.sdk.capture.receipt.capacitor.ScanTypeEnum

class ReqScan(data: JSObject) {
    val scanType: ScanTypeEnum
    val account: Account?

    init {
        scanType = ScanTypeEnum.fromString(data.getString("username") ?: "")
        account = if (data.getString("username") != "" &&  data.getString("source") != ""){
            Account(
                AccountCommon.fromString(data.getString("source")?: ""),
                data.getString("username") ?: "",
                data.getString("password"),
                isVerified = data.getBool("isVerified")
            )
        } else{
            null
        }

    }
}