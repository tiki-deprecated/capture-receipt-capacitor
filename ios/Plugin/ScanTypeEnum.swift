/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import Foundation

public enum ScanTypeEnum : String, CaseIterable{
    // Represens an email scan.
    case EMAIL
    // Represents a retailer scan.
    case RETAILER
    // Represents a physical scan.
    case PHYSICAL
    // Represents an online scan.
    case ONLINE
}
