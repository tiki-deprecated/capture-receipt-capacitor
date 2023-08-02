/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AdditionalLine } from './additional-line';
import type { FloatType } from './float-type';
import type { StringType } from './string-type';

export interface Product {
  productNumber?: StringType;
  description?: StringType;
  quantity?: FloatType;
  unitPrice?: FloatType;
  unitOfMeasure?: StringType;
  totalPrice?: FloatType;
  fullPrice: number;
  line: number;
  productName?: string;
  brand?: string;
  category?: string;
  size?: string;
  rewardsGroup?: string;
  competitorRewardsGroup?: string;
  upc?: string;
  imageUrl?: string;
  shippingStatus?: string;
  additionalLines?: AdditionalLine[];
  priceAfterCoupons?: FloatType;
  voided: boolean;
  probability: number;
  sensitive: boolean;
  possibleProducts?: Product[];
  subProducts?: Product[];
  added: boolean;
  blinkReceiptBrand?: string;
  blinkReceiptCategory?: string;
  extendedFields?: Map<string, string>;
  fuelType?: string;
  descriptionPrefix?: StringType;
  descriptionPostfix?: StringType;
  skuPrefix?: StringType;
  skuPostfix?: StringType;
  attributes?: Map<string, string>[];
  sector?: string;
  department?: string;
  majorCategory?: string;
  subCategory?: string;
  itemType?: string;
}
