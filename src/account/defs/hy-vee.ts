/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import HyVeeIcon from '../images/hy-vee.png';

/**
 * HyVee account type.
 */
export const HY_VEE: AccountType = {
  type: 'RETAILER',
  icon: HyVeeIcon,
  id: 'HYVEE',
  name: 'HyVee',
};
