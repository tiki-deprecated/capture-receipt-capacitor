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
   * The provider of the account, if applicable.
   */
  provider?: AccountProvider;

  /**
   * Indicates whether the account linkage has been verified.
   */
  verified?: boolean;
}

/**
 * Enumeration of possible account providers.
 */
export enum AccountProvider {
  /**
   * Google Mail (Gmail) account provider.
   */
  GMAIL = 'GMAIL',
}

/**
 * A reverse lookup map to locate the {@link AccountProvider} by string value.
 */
export const providers: Map<string, AccountProvider> = new Map(
  Object.values(AccountProvider).map((value) => [`${value}`, value] as const),
);
