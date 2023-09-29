/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import DoorDashIcon from '../images/door-dash.png';

/**
 * DoorDash account type.
 */
export const DOOR_DASH: AccountType = {
  type: 'RETAILER',
  icon: DoorDashIcon,
  name: 'DoorDash',
  id: 'DOOR_DASH',
};
