/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import SamsClubIcon from '../images/sams-club.png';

/**
 * Sam’s Club account type.
 */
export const SAMS_CLUB: AccountType = {
  type: 'RETAILER',
  icon: SamsClubIcon,
  name: 'Sam’s Club',
  id: 'SAMS_CLUB',
};
