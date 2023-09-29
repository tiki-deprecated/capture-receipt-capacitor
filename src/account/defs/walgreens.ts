/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import WalgreensIcon from '../images/walgreens.png';

/**
 * Walgreens account type.
 */
export const WALGREENS: AccountType = {
  type: 'RETAILER',
  icon: WalgreensIcon,
  name: 'Walgreens',
  id: 'WALGREENS',
};
