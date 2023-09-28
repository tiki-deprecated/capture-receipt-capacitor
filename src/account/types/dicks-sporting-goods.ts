/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import DicksIcon from '../images/dicks.png';

/**
 * Dick’s Sporting Goods account type.
 */
export const DICKS_SPORTING_GOODS: AccountType = {
  type: 'RETAILER',
  icon: DicksIcon,
  name: 'Dick’s Sporting Goods',
  id: 'DICKS_SPORTING_GOODS',
};
