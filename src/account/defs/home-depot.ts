/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import HomeDepotIcon from '../images/home-depot.png';

/**
 * Home Depot account type.
 */
export const HOME_DEPOT: AccountType = {
  type: 'RETAILER',
  icon: HomeDepotIcon,
  name: 'Home Depot',
  id: 'HOME_DEPOT',
};
