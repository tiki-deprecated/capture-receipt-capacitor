/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import DollarGeneralIcon from '../images/dollar-general.png';

/**
 * Dollar General account type.
 */
export const DOLLAR_GENERAL: AccountType = {
  type: 'RETAILER',
  icon: DollarGeneralIcon,
  name: 'Dollar General',
  id: 'DOLLAR_GENERAL',
};
