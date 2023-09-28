/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import ShiptIcon from '../images/shipt.png';

/**
 * Shipt account type.
 */
export const SHIPT: AccountType = {
  type: 'RETAILER',
  icon: ShiptIcon,
  name: 'Shipt',
  id: 'SHIPT',
};
