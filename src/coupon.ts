/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { FloatType } from './float-type';
import type { StringType } from './string-type';

export interface Coupon {
  type?: string;
  amount?: FloatType;
  sku?: FloatType;
  description?: StringType;
  relatedProductIndex: number;
}
