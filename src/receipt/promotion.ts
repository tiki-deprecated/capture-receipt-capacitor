/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents a promotion with its associated details and qualifications.
 */
export interface Promotion {
  /**
   * The unique identifier for the promotion.
   */
  id?: number;

  /**
   * The slug or short identifier for the promotion.
   */
  slug?: string;

  /**
   * The amount of the reward offered by the promotion.
   */
  reward?: number;

  /**
   * The currency in which the reward is provided, if applicable.
   */
  rewardCurrency?: string;

  /**
   * The error code associated with the promotion, if applicable.
   */
  errorCode: number;

  /**
   * A message describing the error associated with the promotion, if applicable.
   */
  errorMessage?: string;

  /**
   * An array of related product indexes that the promotion is linked to.
   */
  relatedProductIndexes?: number[];

  /**
   * An array of arrays containing qualification details for the promotion.
   * Each sub-array represents a set of qualifications required for the promotion.
   */
  qualifications?: number[][];
}
