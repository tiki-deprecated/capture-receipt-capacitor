/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import RiteAidIcon from '../images/rite-aid.png';

/**
 * RiteAid account type.
 */
export const RITE_AID: AccountType = {
  type: 'RETAILER',
  icon: RiteAidIcon,
  name: 'RiteAid',
  id: 'RITE_AID',
};
