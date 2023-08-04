/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account, AccountProvider } from './account';
import { providers } from './account';
import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';

export class ReceiptCapture {
  private plugin: ReceiptCapturePlugin;

  constructor(plugin: ReceiptCapturePlugin) {
    this.plugin = plugin;
  }

  initialize = async (
    licenseKey: string,
    productKey?: string,
  ): Promise<void> => {
    const rsp = await this.plugin.initialize({
      licenseKey: licenseKey,
      productKey: productKey,
    });
    if (!rsp.isInitialized) {
      throw Error(rsp.reason);
    }
  };

  scan = (): Promise<Receipt> => this.plugin.scan();

  loginWithEmail = async (
    username: string,
    password: string,
    provider: AccountProvider,
  ): Promise<Account> => {
    const rsp = await this.plugin.loginWithEmail({
      username: username,
      password: password,
      provider: provider.valueOf(),
    });
    return {
      username: rsp.username,
      provider: providers.get(rsp.provider),
    };
  };

  scrapeEmail = (callback: (account: Account, receipts: Receipt[]) => void): Promise<void> =>
    this.plugin.scrapeEmail()
        .then((rsp) => {
          callback(
              {
                username: rsp.login.username,
                provider: providers.get(rsp.login.provider),
              },
              rsp.scans)
        });

  removeEmail = async (
    username: string,
    password: string,
    provider: AccountProvider,
  ): Promise<void> => {
    const rsp = await this.plugin.removeEmail({
      username: username,
      password: password,
      provider: provider.valueOf(),
    });
    if (!rsp.success)
      throw Error(`Failed to remove: ${provider} | ${username}`);
  };

  verifyEmail = async (): Promise<Account[]> => {
    const rsp = await this.plugin.verifyEmail();
    return rsp.accounts.map(acc => {
      return {
        username: acc.username,
        verified: acc.verified,
        provider: providers.get(acc.provider),
      };
    });
  };
}
