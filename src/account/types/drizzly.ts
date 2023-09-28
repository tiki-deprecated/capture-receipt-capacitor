/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import DrizlyIcon from '../images/drizly.png';

/**
 * Drizly account type.
 */
export const DRIZLY: AccountType = {
  type: 'RETAILER',
  icon: DrizlyIcon,
  name: 'Drizly',
  id: 'DRIZLY',
};
