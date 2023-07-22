/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.rsp

import org.json.JSONObject

interface Rsp {
    fun toJson(): JSONObject
}