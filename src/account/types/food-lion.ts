/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import FoodLionIcon from '../images/food-lion.png';

/**
 * Food Lion account type.
 */
export const FOOD_LION: AccountType = {
  type: 'RETAILER',
  icon: FoodLionIcon,
  name: 'Food Lion',
  id: 'FOOD_LION',
};
