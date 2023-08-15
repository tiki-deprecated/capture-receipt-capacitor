/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Product } from './product';

/**
 * Represents a shipment with its status and a list of products included in the shipment.
 */
export interface Shipment {
  /**
   * The status of the shipment.
   */
  status: string;

  /**
   * An array of products included in the shipment.
   */
  products: Product[];
}
