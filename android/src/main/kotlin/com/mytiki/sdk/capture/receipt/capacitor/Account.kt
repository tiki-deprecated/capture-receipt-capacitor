package com.mytiki.sdk.capture.receipt.capacitor

import android.os.Build
import androidx.annotation.RequiresApi
import com.getcapacitor.JSObject
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccountList
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class Account(
    val accountType: AccountCommon,
    val username: String,
    val password: String? = null,
    var isVerified: Boolean? = null
) {
    fun toRsp()= JSObject.fromJSONObject(RspAccount(this).toJson())
    companion object{
        fun fromReq(data: JSObject): Account {
            val req = ReqAccount(data)
            return Account(req.accountType, req.username, req.password, req.isVerified)
        }

        fun fromMbLinking(mbAccount: com.microblink.linking.Account): Account{
            val accountType = AccountCommon.fromString(RetailerEnum.fromInt(mbAccount.retailerId).toString())
            val username = mbAccount.credentials.username()
            return Account(accountType, username)
        }

        fun fromMbEmail(mbAccount:  PasswordCredentials): Account{
            val accountType = AccountCommon.fromString(mbAccount.provider().name)
            val username = mbAccount.username()
            return Account(accountType, username)
        }

        @RequiresApi(Build.VERSION_CODES.O_MR1)
        fun toRspList(list: List<Account>, error: Exception? = null): JSObject = JSObject.fromJSONObject(RspAccountList(list.toMutableList(), error).toJson())
    }
    
}