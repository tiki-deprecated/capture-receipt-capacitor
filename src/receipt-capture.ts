/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from './account';
import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin, ScanType } from './receipt-capture-plugin';

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
   * Initializes the receipt capture plugin with a license key and optional product intelligence key.
   * @param licenseKey - The license key for your application.
   * @param productKey - The optional product intelligence key for your application.
   * @throws Error if the initialization fails.
   */
  initialize = async (licenseKey: string, productKey?: string): Promise<void> => {
    const rsp = await this.plugin.initialize({
      licenseKey: licenseKey,
      productKey: productKey,
    });
    if (!rsp.isInitialized) {
      throw Error(rsp.reason);
    }
  };

  /**
   * Initiates the receipt scan UI and returns the scanned receipt.
   * @returns A Promise that resolves to the scanned Receipt object.
   */
  scan = (scanType: ScanType | undefined, account?: Account): Promise<{receipt: Receipt, isRunning: boolean}> => this.plugin.scan({scanType, account});

  /**
   * Logs in to an email account using IMAP.
   * @param username - The account username.
   * @param password - The account password.
   * @param provider - The {@link AccountProvider}.
   * @returns A Promise that resolves to the logged-in Account object.
   */
  loginWithEmail = async (username: string, password: string, provider: string): Promise<Account> => {
    const rsp = await this.plugin.loginWithEmail({
      username: username,
      password: password,
      provider: provider,
    });
    return {
      username: rsp.username,
      accountType: {
        type: 'EMAIL',
        name: provider,
        icon: undefined,
        key: provider
      }
      //provider: providers.get(rsp.provider),
    };
  };

  /**
   * Logs out and removes an email account from the local cache.
   * @param username - The account username.
   * @param password - The account password.
   * @param provider - The {@link AccountProvider}.
   * @returns A Promise that resolves when the account is removed.
   * @throws Error if removal fails.
   */
  removeEmail = async (username: string, password: string, provider: string): Promise<void> => {
    const rsp = await this.plugin.removeEmail({
      username: username,
      password: password,
      provider: provider,
    });
    if (!rsp.success) throw Error(`Failed to remove: ${provider} | ${username}`);
  };

  loginWithRetailer = async (username: string, password: string, provider: string): Promise<Account> => {
    return this.plugin.loginWithRetailer({
      username,
      password,
      provider,
    });
  };

  accounts = async (): Promise<Account[]> =>{
    return (await this.plugin.accounts())
  }

  removeRetailer = async (username: string, provider: string): Promise<Account> => {
    return await this.plugin.removeRetailer({username, provider});
  };

  flushEmail = async (): Promise<void> => this.plugin.flushEmail();
  flushRetailer = async (): Promise<void> => this.plugin.flushRetailer();
}


