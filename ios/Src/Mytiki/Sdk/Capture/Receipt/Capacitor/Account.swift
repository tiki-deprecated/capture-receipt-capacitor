/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt


public class Account {
    let accountType: AccountType
    let user: String
    let password: String?
    let isVerified: Bool?
}
