/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import HarrisTeeterIcon from '../images/harris-teeter.png';

/**
 * Harris Teeter account type.
 */
export const HARRIS_TEETER: AccountType = {
  type: 'RETAILER',
  icon: HarrisTeeterIcon,
  name: 'Harris Teeter',
  id: 'HARRIS_TEETER',
};
