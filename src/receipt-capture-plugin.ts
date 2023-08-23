/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from './account';
import type { Receipt } from './receipt';


export type ScanType = 'Physical' | 'Email' | 'Retailer';

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
    productKey?: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;



  scan(scanType: ScanType | undefined, account: Account): Promise<Receipt[]>;

  accounts(): Promise<Account[]>;

  loginWithEmail(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<{ username: string; provider: string }>;

  removeEmail(options: { username: string; password: string; provider: string }): Promise<{ success: boolean }>;

  loginWithRetailer(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<Account>;

  removeRetailer(options: { username: string; provider: string }): Promise<Account>;

  flushRetailer(): Promise<void>;

  flushEmail(): Promise<void>;
}
