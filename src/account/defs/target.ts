/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import TargetIcon from '../images/target.png';

/**
 * Target account type.
 */
export const TARGET: AccountType = {
  type: 'RETAILER',
  icon: TargetIcon,
  name: 'Target',
  id: 'TARGET',
};
