/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from './account';
import type { Receipt } from './receipt';

/** The type of the Scan Method
 * the @PHYSICAL type calls the camera to scan a physical receipt
 * the @EMAIL type will call the data from linked emails accounts
 * the @RETAILER type will call the data from linked retailers accounts
 * the @ONLINE type will call the receipts from linked emails and linked retailers accounts
 */
export type ScanType = 'PHYSICAL' | 'EMAIL' | 'RETAILER' | 'ONLINE';

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
    productKey?: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;

  /**
   * scan a physical receipt or an email/retailer account
   * @param _option - the scanType that will decide the path of the function and the account linked to that receipt that will be returned
   * @returns a promise with the scanned receipt and a boolean to indicate the end of the process
   */
  scan(_option: {scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean}>;

  /**
   * loads all the linked accounts
   * @returns a promise with an array of the accounts that are logged in
   */
  accounts(): Promise<Account[]>;


  /**
   * log in with an email account
   * @param options - contains the user credentials and the provider of the email that will be logged in
   * @returns a promise with the username and the email provider
   */
  loginWithEmail(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<Account>;

  /**
   * logs out from an email account
   * @param options - contains the user credentials and the provider of the email that will be logged out
   * @returns a boolean that indicates the sucess of the process
   */
  removeEmail(options: { username: string; password: string; provider: string }): Promise<{ success: boolean }>;

  /**
   * log in with a retailer account
   * @param options - contains the user credentials and the retailer that will be logged in
   * @returns a promise with the Account
   */
  loginWithRetailer(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<Account>;

   /**
   * logs out from a retailer account
   * @param options - contains the user credentials and the retailer that will be logged out
   * @returns the account that was logged out
   */
  removeRetailer(options: { username: string; provider: string }): Promise<Account>;

  /**
   * Removes from cache and log out all the retailers accounts
   */
  flushRetailer(): Promise<void>;

   /**
   * Removes from cache and log out all the emails accounts
   */
  flushEmail(): Promise<void>;
}
