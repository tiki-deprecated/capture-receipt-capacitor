/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import MacysIcon from '../images/macys.png';

/**
 * Macy’s account type.
 */
export const MACYS: AccountType = {
  type: 'RETAILER',
  icon: MacysIcon,
  name: 'Macy’s',
  id: 'MACYS',
};
