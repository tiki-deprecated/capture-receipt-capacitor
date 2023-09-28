/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import GrubHubIcon from '../images/grubhub.png';

/**
 * Grubhub account type.
 */
export const GRUBHUB: AccountType = {
  type: 'RETAILER',
  icon: GrubHubIcon,
  name: 'Grubhub',
  id: 'GRUBHUB',
};
