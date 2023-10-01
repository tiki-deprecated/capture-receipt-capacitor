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
   * The provider of the account, if applicable.
   */
  type: AccountType;

  /**
   * The password associated with the account.
   */
  password?: string;

  /**
   * Indicates whether the account linkage has been verified.
   */
  isVerified?: boolean;

}

export * from './defs';
export * from './account-types';
