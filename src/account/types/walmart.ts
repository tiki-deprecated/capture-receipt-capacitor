/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import WalmartIcon from '../images/walmart.png';

/**
 * Walmart account type.
 */
export const WALMART: AccountType = {
  type: 'RETAILER',
  icon: WalmartIcon,
  name: 'Walmart',
  id: 'WALMART',
};
