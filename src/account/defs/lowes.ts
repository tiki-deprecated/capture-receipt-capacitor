/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import LowesIcon from '../images/lowes.png';

/**
 * Lowe’s account type.
 */
export const LOWES: AccountType = {
  type: 'RETAILER',
  icon: LowesIcon,
  name: 'Lowe’s',
  id: 'LOWES',
};
