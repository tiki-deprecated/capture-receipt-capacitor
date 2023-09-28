/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import AmazonIcon from '../images/amazon.png';

/**
 * Amazon account type.
 */
export const AMAZON: AccountType = {
  type: 'RETAILER',
  icon: AmazonIcon,
  name: 'Amazon',
  id: 'AMAZON',
};
