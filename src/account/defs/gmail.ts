/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from '../account-type';
import GmailIcon from '../images/gmail.png';

/**
 * Gmail account type.
 */
export const GMAIL: AccountType = {
  type: 'EMAIL',
  icon: GmailIcon,
  name: 'Gmail',
  id: 'GMAIL',
};
