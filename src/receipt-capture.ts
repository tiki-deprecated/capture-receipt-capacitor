/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from './account';
import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin, ReqInitialize, ScanType } from './receipt-capture-plugin';

/**
 * The primary class for interacting with the Plugin.
 */
export class ReceiptCapture {
  private plugin: ReceiptCapturePlugin;

  /**
   * Constructs a new instance of the ReceiptCapture class. Do not construct
   * directly, use {@link instance}.
   * @param plugin - The ReceiptCapturePlugin instance used for communication.
   */
  constructor(plugin: ReceiptCapturePlugin) {
    this.plugin = plugin;
  }

  /**
   * Initializes the receipt capture plugin with a license key and product intelligence key.
   * @param licenseKey - The license key for your application.
   * @param productKey - The optional product intelligence key for your application.
   * @throws Error if the initialization fails.
   */
  initialize = async (licenseKey: string, productKey: string): Promise<void> => {
    const req: ReqInitialize = {
      licenseKey: licenseKey,
      productKey: productKey,
    }
    await this.plugin.initialize(req).catch( (error) => {
      throw Error(error);
    })
  };

  /**
   * Login into a retailer or email account to scan for receipts.
   * @param username - the username of the account.
   * @param password - the password of the account
   * @param source - the source from that account, that can be an email service or a retailer service.
   * @returns - the Account interface with the logged in information.
   */
  login = (username: string, password: string, source: string): Promise<Account> => this.plugin.login({username, password, source})

  /**
   * Log out from one or all {@link Account}.
   * @param username - the username of the account that will be logged out.
   * @param password - the password of the account that will be logged out
   * @param source - the source from that account, that can be an email service or a retailer service.
   * @returns - the Account that logged out
   */
  logout = (username: string, password: string, source: string): Promise<Account> => this.plugin.logout({username, password, source})

  /**
   * Scan for receipts. That can be a physical one, the receipts from an email/retailer account, or all receipts.
   * @param scanType - The type of the scan.
   * @param account - The account that will be scanned for receipts.
   * @returns - The scanned Receipt and a boolean indicates the execution.
   */
  scan = (scanType: ScanType | undefined, account?: Account): Promise<{receipt: Receipt, isRunning: boolean}> => this.plugin.scan({scanType, account})

  /**
   * Retrieves all saved accounts.
   * @returns - an array of Accounts.
   */
  accounts = async (): Promise<Account[]> => await this.plugin.accounts()
}
