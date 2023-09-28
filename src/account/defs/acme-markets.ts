/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import AcmeIcon from '../images/acme.png';

/**
 * ACME Markets account type
 */
export const ACME_MARKETS: AccountType = {
  type: 'RETAILER',
  name: 'Acme',
  icon: AcmeIcon,
  id: 'ACME_MARKETS',
};
