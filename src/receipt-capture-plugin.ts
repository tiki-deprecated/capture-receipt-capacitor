/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from './account';
import type { Receipt } from './receipt';


export type ScanType = 'PHYSICAL' | 'EMAIL' | 'RETAILER' | 'ONLINE';

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
    productKey?: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;

  login(options: {
    username: string,
    password: string,
    source: string,
  }): Promise<Account>

  logout(options?: {
    username: string,
    password: string,
    source: string,
  }): Promise<Account>

  scan(_option: {scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean}>;

  accounts(): Promise<Account[]>;
}
