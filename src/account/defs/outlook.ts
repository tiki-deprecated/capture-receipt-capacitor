/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import OutlookIcon from '../images/outlook.png';

/**
 * Microsoft Outlook account type.
 */
export const OUTLOOK: AccountType = {
  name: 'Outlook',
  type: 'EMAIL',
  icon: OutlookIcon,
  id: 'OUTLOOK',
};
