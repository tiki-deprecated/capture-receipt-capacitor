/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import SafewayIcon from '../images/safeway.png';

/**
 * Safeway account type.
 */
export const SAFEWAY: AccountType = {
  type: 'RETAILER',
  icon: SafewayIcon,
  name: 'Safeway',
  id: 'SAFEWAY',
};
