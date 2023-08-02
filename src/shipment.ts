/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Product } from './product';

export interface Shipment {
  status: string;
  products: Product[];
}
