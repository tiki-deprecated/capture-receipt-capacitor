/*
 * RspStringType Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import BlinkReceipt
import BlinkEReceipt
import Capacitor

/**
 Represents a response containing retailer identification information

 This struct is used to convey a retailer id value along with its banner id.
 */
struct JSRetailer{
    /// The retailer's id
    private let id: Int
    /// The retailer's banner id
    private let bannerId: Int?

    /**
     Initializes an `JSStringType` struct.

     - Parameters:
        - retailer: The retailer value.
     */
    init(retailer: WFRetailerId) {
        id = Int(retailer.rawValue)
        bannerId = nil
    }

    /**
     Converts the `JSStringType` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the id value and its banner id in a format suitable for a Capacitor plugin call result.
     */
    public func toJSObject() -> JSObject {
        var ret = JSObject()
        ret["id"] = id
        ret["bannerId"] = bannerId
        return ret
    }
}
