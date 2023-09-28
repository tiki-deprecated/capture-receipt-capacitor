/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import BestBuyIcon from '../images/best-buy.png';

/**
 * Best Buy account type.
 */
export const BEST_BUY: AccountType = {
  type: 'RETAILER',
  icon: BestBuyIcon,
  name: 'Best Buy',
  id: 'BESTBUY',
};
