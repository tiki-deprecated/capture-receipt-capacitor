/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import KrogerIcon from '../images/kroger.png';

/**
 * Kroger account type.
 */
export const KROGER: AccountType = {
  type: 'RETAILER',
  icon: KrogerIcon,
  name: 'Kroger',
  id: 'KROGER',
};
