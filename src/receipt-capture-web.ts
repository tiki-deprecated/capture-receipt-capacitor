/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';
import type { Account } from './account';
import type { ScanType } from './receipt-capture-plugin';

export class ReceiptCaptureWeb extends WebPlugin implements ReceiptCapturePlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
   /**
   * Initializes the receipt capture plugin with a license key and product intelligence key.
   * @param licenseKey - The license key for your application.
   * @param productKey - The optional product intelligence key for your application.
   * @throws Error if the initialization fails.
   */
  async initialize(){
    throw this.unimplemented('Mobile Only.');
  }

   /**
   * Login into a retailer or email account to scan for receipts.
   * @param username - the username of the account.
   * @param password - the password of the account
   * @param source - the source from that account, that can be an email service or a retailer service.
   * @returns - the Account interface with the logged in information.
   */
  async login(): Promise<Account>{
    throw this.unimplemented('Mobile Only')
  }

  /**
   * Log out from one or all {@link Account}.
   * @param username - the username of the account that will be logged out.
   * @param password - the password of the account that will be logged out
   * @param source - the source from that account, that can be an email service or a retailer service.
   * @returns - the Account that logged out
   */
  async logout(): Promise<Account>{
    throw this.unimplemented('Mobile Only')
  }

   /**
   * Scan for receipts. That can be a physical one, the receipts from an email/retailer account, or all receipts.
   * @param scanType - The type of the scan.
   * @param account - The account that will be scanned for receipts.
   * @returns - The scanned Receipt and a boolean indicates the execution.
   */
  async scan(_options:{scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean, account?: Account}> {
    throw this.unimplemented('Mobile Only.');
  }

  /**
   * Retrieves all saved accounts.
   * @returns - an array of Accounts.
   */
  async accounts(): Promise<Account[]>{
    throw this.unimplemented('Mobile Only.');
  };
}
