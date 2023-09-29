/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import KohlsIcon from '../images/kohls.png';

/**
 * Kohl’s account type.
 */
export const KOHLS: AccountType = {
  type: 'RETAILER',
  icon: KohlsIcon,
  name: 'Kohl’s',
  id: 'KOHLS',
};
