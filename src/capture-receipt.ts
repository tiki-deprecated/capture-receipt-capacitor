/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { ReceiptService } from "./ReceiptService";
/**
 * The primary class for interacting with the Plugin.
 */
export class CaptureReceipt {
    static receiptService = new ReceiptService()

}
