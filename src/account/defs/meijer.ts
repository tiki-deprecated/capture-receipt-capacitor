/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import MeijerIcon from '../images/meijer.png';

/**
 * Meijer account type.
 */
export const MEIJER: AccountType = {
  type: 'RETAILER',
  icon: MeijerIcon,
  name: 'Meijer',
  id: 'MEIJER',
};
