/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AdditionalLine } from './interfaces/additional-line';
import type { FloatType } from './float-type';
import type { StringType } from './string-type';

/**
 * Represents a product with various attributes and details.
 */
export interface Product {
  /**
   * The product number associated with the product.
   */
  productNumber?: StringType;

  /**
   * The description of the product.
   */
  description?: StringType;

  /**
   * The quantity of the product.
   */
  quantity?: FloatType;

  /**
   * The unit price of the product.
   */
  unitPrice?: FloatType;

  /**
   * The unit of measure for the product.
   */
  unitOfMeasure?: StringType;

  /**
   * The total price of the product.
   */
  totalPrice?: FloatType;

  /**
   * The full price of the product.
   */
  fullPrice: number;

  /**
   * The line number associated with the product.
   */
  line: number;

  /**
   * The name of the product.
   */
  productName?: string;

  /**
   * The brand of the product.
   */
  brand?: string;

  /**
   * The category of the product.
   */
  category?: string;

  /**
   * The size of the product.
   */
  size?: string;

  /**
   * The rewards group associated with the product.
   */
  rewardsGroup?: string;

  /**
   * The competitor's rewards group associated with the product.
   */
  competitorRewardsGroup?: string;

  /**
   * The UPC (Universal Product Code) of the product.
   */
  upc?: string;

  /**
   * The URL to the product's image.
   */
  imageUrl?: string;

  /**
   * The shipping status of the product.
   */
  shippingStatus?: string;

  /**
   * An array of additional lines of information related to the product.
   */
  additionalLines?: AdditionalLine[];

  /**
   * The price of the product after applying coupons.
   */
  priceAfterCoupons?: FloatType;

  /**
   * Indicates whether the product is voided.
   */
  voided: boolean;

  /**
   * The probability associated with the product.
   */
  probability: number;

  /**
   * Indicates whether the product is sensitive.
   */
  sensitive: boolean;

  /**
   * An array of possible products associated with this product.
   */
  possibleProducts?: Product[];

  /**
   * An array of sub-products associated with this product.
   */
  subProducts?: Product[];

  /**
   * Indicates whether the product has been added.
   */
  added: boolean;

  /**
   * The brand associated with the product in the Blink Receipt dataset.
   */
  blinkReceiptBrand?: string;

  /**
   * The category associated with the product in the Blink Receipt dataset.
   */
  blinkReceiptCategory?: string;

  /**
   * A map of extended fields associated with the product.
   */
  extendedFields?: Map<string, string>;

  /**
   * The fuel type associated with the product.
   */
  fuelType?: string;

  /**
   * The prefix for the product description.
   */
  descriptionPrefix?: StringType;

  /**
   * The postfix for the product description.
   */
  descriptionPostfix?: StringType;

  /**
   * The prefix for the product SKU (Stock Keeping Unit).
   */
  skuPrefix?: StringType;

  /**
   * The postfix for the product SKU (Stock Keeping Unit).
   */
  skuPostfix?: StringType;

  /**
   * An array of attributes associated with the product.
   */
  attributes?: Map<string, string>[];

  /**
   * The sector of the product.
   */
  sector?: string;

  /**
   * The department of the product.
   */
  department?: string;

  /**
   * The major category of the product.
   */
  majorCategory?: string;

  /**
   * The sub-category of the product.
   */
  subCategory?: string;

  /**
   * The type of the item.
   */
  itemType?: string;
}
