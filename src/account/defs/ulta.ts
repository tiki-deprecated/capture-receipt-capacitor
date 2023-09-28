/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import UltaIcon from '../images/ulta.png';

/**
 * Ulta account type.
 */
export const ULTA: AccountType = {
  type: 'RETAILER',
  icon: UltaIcon,
  name: 'Ulta',
  id: 'ULTA',
};
