package com.mytiki.sdk.capture.receipt.capacitor

import android.os.Build
import androidx.annotation.RequiresApi
import com.getcapacitor.JSObject
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccountList

class Account(
    val accountCommon: AccountCommon,
    val username: String,
    val password: String? = null,
    var isVerified: Boolean? = null
) {
    fun toRsp(): JSObject = JSObject.fromJSONObject(RspAccount(this).toJson())
    companion object{
        fun fromReq(data: JSObject): Account {
            val req = ReqAccount(data)
            return Account(req.accountCommon, req.username, req.password, req.isVerified)
        }

        fun fromRetailerAccount(mbAccount: com.microblink.linking.Account): Account{
            val accountType = AccountCommon.fromString(RetailerEnum.fromMbInt(mbAccount.retailerId).toString())
            val username = mbAccount.credentials.username()
            return Account(accountType, username)
        }

        fun fromEmailAccount(mbAccount:  PasswordCredentials): Account{
            val accountType = AccountCommon.fromString(mbAccount.provider().name)
            val username = mbAccount.username()
            return Account(accountType, username)
        }

        fun toRspList(list: List<Account>, error: Exception? = null): JSObject = JSObject.fromJSONObject(RspAccountList(list.toMutableList(), error).toJson())
    }
}