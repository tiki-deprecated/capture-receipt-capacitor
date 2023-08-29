/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';
import { Account } from './account';
import type { ScanType } from './receipt-capture-plugin';

export class ReceiptCaptureWeb extends WebPlugin implements ReceiptCapturePlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  loginWithRetailer(_options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<Account> {
    throw new Error('Method not implemented.');
  }
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  removeRetailer(_options: {
    username: string;
    provider: string;
  }): Promise<Account> {
    throw new Error('Method not implemented.');
  }
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  async initialize(_options: { licenseKey: string }): Promise<{ isInitialized: boolean; reason?: string }> {
    throw this.unimplemented('Mobile Only.');
  }

  async scan(_options:{scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean}> {
    throw this.unimplemented('Mobile Only.');
  }

  async accounts(): Promise<Account[]>{
    throw this.unimplemented('Mobile Only.');
  };

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  loginWithEmail(_options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<Account> {
    throw this.unimplemented('Mobile Only.');
  }

  scrapeEmail(): Promise<{
    login: { username: string; provider: string };
    scans: Receipt[];
  }> {
    throw this.unimplemented('Mobile Only.');
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  removeEmail(_options: { username: string; password: string; provider: string }): Promise<{ success: boolean }> {
    throw this.unimplemented('Mobile Only.');
  }

  flushRetailer(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }

  flushEmail(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }
}
