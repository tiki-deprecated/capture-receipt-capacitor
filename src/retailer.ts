/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents a retailer with its unique identifier and banner identifier.
 */
export interface Retailer {
  /**
   * The unique identifier for the retailer.
   */
  id: number;

  /**
   * The unique identifier for the banner associated with the retailer.
   */
  bannerId: number;
}
