/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { FloatType } from './float-type';
import type { StringType } from './string-type';

/**
 * Represents a coupon applied to a purchase, along with its details.
 */
export interface Coupon {
  /**
   * The type or category of the coupon.
   */
  type?: string;

  /**
   * The amount associated with the coupon. This includes the value and confidence level.
   */
  amount?: FloatType;

  /**
   * The SKU (Stock Keeping Unit) associated with the coupon. This includes the value and confidence level.
   */
  sku?: StringType;

  /**
   * A description or additional information about the coupon.
   */
  description?: StringType;

  /**
   * The index of the related product for which the coupon is applicable.
   */
  relatedProductIndex: number;
}
