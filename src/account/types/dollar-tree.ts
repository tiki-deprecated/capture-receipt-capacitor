/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import DollarTreeIcon from '../images/dollar-tree.png';

/**
 * DollarTree account type.
 */
export const DOLLAR_TREE: AccountType = {
  type: 'RETAILER',
  icon: DollarTreeIcon,
  name: 'DollarTree',
  id: 'DOLLAR_TREE',
};
