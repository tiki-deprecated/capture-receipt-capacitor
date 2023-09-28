/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import StarbucksIcon from '../images/starbucks.png';

/**
 * Starbucks account type.
 */
export const STARBUCKS: AccountType = {
  type: 'RETAILER',
  icon: StarbucksIcon,
  name: 'Starbucks',
  id: 'STARBUCKS',
};
