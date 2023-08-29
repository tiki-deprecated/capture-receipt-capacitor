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

/** The interface with the 3rd-party account info
 * @type - indicates if the account its from an email service or a retailer service
 * @name - The name of the email/retailer service
 * @icon - the icon of the email/retailer service.
 * @key - the key of the enum value, that will be send to tiki-capture-receipt-capacitor
 */
export interface AccountType {
  type: 'EMAIL' | 'RETAILER',
  name: string,
  icon?: string,
  key: string
}

