/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import AmazonIcon from '../images/amazon.png';

/**
 * Amazon Canada account type.
 */
export const AMAZON_CA: AccountType = {
  type: 'RETAILER',
  icon: AmazonIcon,
  name: 'Amazon Canada',
  id: 'AMAZON_CA',
};
