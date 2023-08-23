/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

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
  accountType: AccountType;

  /**
   * Indicates whether the account linkage has been verified.
   */
  isVerified?: boolean;
}

/**
 * Enumeration of possible account providers.
 */
export interface AccountType {
  type: 'Email' | 'Retailer',
  name: string,
  icon?: string,
  key: string
}

// /**
//  * A reverse lookup map to locate the {@link AccountProvider} by string value.
//  */
// export const providers: Map<string, AccountProvider> = new Map(
//   Object.values(AccountProvider).map((value) => [`${value}`, value] as const),
// );
