package com.mytiki.sdk.capture.receipt.capacitor

import android.os.Build
import androidx.annotation.RequiresApi
import com.getcapacitor.JSObject
import com.google.android.gms.common.internal.AccountType
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccountList
import java.security.CodeSource

class Account(
    val accountType: AccountTypeEnum,
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
            val accountType = AccountTypeEnum.fromString(RetailerEnum.fromInt(mbAccount.retailerId).toString())
            val username = mbAccount.credentials.username()
            return Account(accountType, username)
        }

        fun fromMbEmail(mbAccount:  PasswordCredentials): Account{
            val accountType = AccountTypeEnum.fromString(mbAccount.provider().name)
            val username = mbAccount.username()
            return Account(accountType, username)
        }

        @RequiresApi(Build.VERSION_CODES.O_MR1)
        fun toRspList(list: List<Account>, error: Exception? = null): JSObject = JSObject.fromJSONObject(RspAccountList(list.toMutableList(), error).toJson())
    }
    
}