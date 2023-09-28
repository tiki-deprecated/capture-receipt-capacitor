/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import StaplesIcon from '../images/staples.png';

/**
 * Staples account type.
 */
export const STAPLES: AccountType = {
  type: 'RETAILER',
  icon: StaplesIcon,
  name: 'Staples',
  id: 'STAPLES',
};
