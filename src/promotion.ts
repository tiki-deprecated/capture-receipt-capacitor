/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface Promotion {
  id: number;
  slug?: string;
  reward?: string;
  rewardCurrency?: string;
  errorCode: number;
  errorMessage?: string;
  relatedProductIndexes?: number[];
  qualifications?: number[][];
}
