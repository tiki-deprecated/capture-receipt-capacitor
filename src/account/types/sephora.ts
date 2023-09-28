/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import SephoraIcon from '../images/sephora.png';

/**
 * Sephora account type.
 */
export const SEPHORA: AccountType = {
  type: 'RETAILER',
  icon: SephoraIcon,
  name: 'Sephora',
  id: 'SEPHORA',
};
