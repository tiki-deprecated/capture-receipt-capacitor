/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { AccountType } from './account-type';

/**
 * Represents a user's 3rd party account.
 */
export interface Account {
  /**
   * The username associated with the account.
   */
  username: string;

  /**
   * The password associated with the account.
   */
  password?: string;

  /**
   * The provider of the account, if applicable.
   */
  type: AccountType;

  /**
   * Indicates whether the account linkage has been verified.
   */
  isVerified?: boolean;
}

export * from './defs';
export * from './account-types';
