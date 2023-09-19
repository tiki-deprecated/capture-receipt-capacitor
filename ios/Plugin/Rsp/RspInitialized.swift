/*
 * RspInitialized Struct
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation
import Capacitor

/**
 Represents a response indicating whether the ReceiptCapture plugin is initialized.

 This struct is used to convey information about the initialization status of the ReceiptCapture plugin.
 */
struct RspInitialized: Rsp {
    /// A flag indicating whether the plugin is initialized.
    private let isInitialized: Bool

    /**
     Initializes an `RspInitialized` struct.

     - Parameter isInitialized: A boolean flag indicating whether the ReceiptCapture plugin is initialized.
     */
    init(isInitialized: Bool) {
        self.isInitialized = isInitialized
    }

    /**
     Converts the `RspInitialized` struct into a dictionary suitable for use in plugin response data.

     - Returns: A dictionary containing the initialization status in a format suitable for a Capacitor plugin call result.
     */
    func toPluginCallResultData() -> Capacitor.PluginCallResultData {
        return [
            "isInitialized": isInitialized
        ]
    }
}

