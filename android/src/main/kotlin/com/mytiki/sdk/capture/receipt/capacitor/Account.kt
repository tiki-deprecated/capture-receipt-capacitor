package com.mytiki.sdk.capture.receipt.capacitor

import com.getcapacitor.JSObject
import com.microblink.digital.PasswordCredentials
import com.mytiki.sdk.capture.receipt.capacitor.req.ReqAccount
import com.mytiki.sdk.capture.receipt.capacitor.rsp.RspAccount

/**
 * Represents information about an account.
 *
 * This class defines the properties of an Account, including common account details,
 * username, password, and verification status.
 *
 * @property accountCommon The source of the account. See [AccountCommon]
 * @property username The username associated with the account.
 * @property password The password associated with the account. (Optional)
 * @property isVerified Indicates whether the account is verified or not. (Optional)
 * @constructor Creates an empty Account.
 */
class Account(
    val accountCommon: AccountCommon,
    val username: String,
    val password: String? = null,
    var isVerified: Boolean? = null
) {
    /**
     * Converts an [Account] to a [JSObject].
     *
     * The [JSObject] is used to pass data to a Capacitor plugin.
     *
     * @return JSObject representation of the Account.
     */
    fun toRsp(requestId: String): JSObject = RspAccount(requestId, this).toJS()

    companion object {
        /**
         * Converts data from a [JSObject] into an [Account] object.
         *
         * @param data Data received from a Capacitor plugin.
         * @return Account object.
         */
        fun fromReq(data: JSObject): Account {
            val req = ReqAccount(data)
            return Account(req.accountCommon, req.username, req.password, req.isVerified)
        }

        /**
         * Converts a [com.microblink.linking.Account] into an [Account] object.
         *
         * @param retailerAccount [com.microblink.linking.Account] object.
         * @return Account object.
         */
        fun fromRetailerAccount(retailerAccount: com.microblink.linking.Account): Account {
            val accountType = AccountCommon.fromSource(
                RetailerEnum.fromMbInt(retailerAccount.retailerId).toString()
            )
            val username = retailerAccount.credentials.username()
            return Account(accountType, username)
        }

        /**
         * Converts a [PasswordCredentials] into an [Account] object.
         *
         * @param emailAccount [PasswordCredentials] object.
         * @return Account object.
         */
        fun fromEmailAccount(emailAccount: PasswordCredentials): Account {
            val accountType = AccountCommon.fromSource(emailAccount.provider().name)
            val username = emailAccount.username()
            return Account(accountType, username)
        }

    }
}
