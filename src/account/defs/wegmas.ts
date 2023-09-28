/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import WegmansIcon from '../images/wegmans.png';

/**
 * Wegman’s account type.
 */
export const WEGMANS: AccountType = {
  type: 'RETAILER',
  icon: WegmansIcon,
  name: 'Wegman’s',
  id: 'WEGMANS',
};
