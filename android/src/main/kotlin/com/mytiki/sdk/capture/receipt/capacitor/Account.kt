package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccountList

/**
 * Account information
 *
 * This class defines the properties of an Account
 *
 * @property accountCommon  tells from where the account is
 * @property username the [Account] username
 * @property password the [Account] password
 * @property isVerified tells if the account is verified or not
 * @constructor Create empty Account
 */
class Account(
    val accountCommon: AccountCommon,
    val username: String,
    val password: String? = null,
    var isVerified: Boolean? = null
) {
    /**
     * Convert an [Account] to a [JSObject]
     *
     * the [JSObject] is used to pass data to capacitor plugin
     * @return JSObject
     */
    fun toRsp(): JSObject = JSObject.fromJSONObject(RspAccount(this).toJson())
    companion object{

        /**
         * Convert a [data] into an [Account] object.
         *
         * @param data data received from capacitor plugin
         * @return Account
         */
        fun fromReq(data: JSObject): Account {
            val req = ReqAccount(data)
            return Account(req.accountCommon, req.username, req.password, req.isVerified)
        }

        /**
         * Convert [com.microblink.linking.Account] into an [Account] object.
         *
         * @param mbAccount [com.microblink.linking.Account] object
         * @return
         */
        fun fromRetailerAccount(mbAccount: com.microblink.linking.Account): Account{
            val accountType = AccountCommon.fromString(RetailerEnum.fromMbInt(mbAccount.retailerId).toString())
            val username = mbAccount.credentials.username()
            return Account(accountType, username)
        }

        /**
         * Convert a [PasswordCredentials] into an [Account] object.
         *
         * @param mbAccount [PasswordCredentials] object
         * @return
         */
        fun fromEmailAccount(mbAccount:  PasswordCredentials): Account{
            val accountType = AccountCommon.fromString(mbAccount.provider().name)
            val username = mbAccount.username()
            return Account(accountType, username)
        }

        /**
         * Convert a [List]<[Account]> to a [JSObject] array
         * the [JSObject] is used to pass data to capacitor plugin
         *
         * @param list list of [Account]
         * @param error errors caught when getting the [List]<[Account]>
         * @return
         */
        fun toRspList(list: List<Account>, error: Exception? = null): JSObject = JSObject.fromJSONObject(RspAccountList(list.toMutableList(), error).toJson())
    }
}