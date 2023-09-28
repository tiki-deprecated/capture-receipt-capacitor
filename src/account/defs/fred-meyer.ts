/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import FredMeyerIcon from '../images/fred-meyer.png';

/**
 * Fred Meyer account type.
 */
export const FRED_MEYER: AccountType = {
  type: 'RETAILER',
  icon: FredMeyerIcon,
  name: 'Fred Meyer',
  id: 'FRED_MEYER',
};
