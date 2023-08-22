package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.google.android.gms.common.internal.AccountType
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount
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

        fun fromMb(mbAccount: com.microblink.linking.Account): Account{
            val accountType = AccountTypeEnum.fromString(RetailerEnum.fromInt(mbAccount.retailerId).toString())
            val username = mbAccount.credentials.username()
            return Account(accountType, username)
        }
    }
    
}