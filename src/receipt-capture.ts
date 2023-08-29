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

  login = (username: string, password: string, source: string): Promise<Account> => this.plugin.login({username, password, source})

  logout = (username: string, password: string, source: string): Promise<Account> => this.plugin.logout({username, password, source})

  scan = (scanType: ScanType | undefined, account?: Account): Promise<{receipt: Receipt, isRunning: boolean}> => this.plugin.scan({scanType, account});

  accounts = async (): Promise<Account[]> =>{
    return (await this.plugin.accounts())
  }

}


