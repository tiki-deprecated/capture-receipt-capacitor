package com.mytiki.sdk.capture.receipt.capacitor

import com.google.android.gms.common.internal.AccountType
import com.microblink.digital.PasswordCredentials
import java.security.CodeSource

class Account(
    val accountType: AccountType,
    val username: String,
    val password: String? = null,
    val isVerified: Boolean? = null
) {
    
}