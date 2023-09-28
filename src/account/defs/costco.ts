/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import CostcoIcon from '../images/costco.png';

/**
 * Costco account type.
 */
export const COSTCO: AccountType = {
  type: 'RETAILER',
  icon: CostcoIcon,
  name: 'Costco',
  id: 'COSTCO',
};
