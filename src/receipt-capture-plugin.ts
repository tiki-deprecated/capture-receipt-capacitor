/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from './account';
import type { Receipt } from './receipt';


export type ScanType = 'PHYSICAL' | 'EMAIL' | 'RETAILER' | 'ONLINE';

export interface ReqAccount {
  username: string,
  password: string,
  source: string,
}

export interface ReqInitialize{
  licenseKey: string;
  productKey: string;
}

export interface ReceiptCapturePlugin {
  initialize(options: ReqInitialize): Promise<void>;

  login(options: ReqAccount): Promise<Account>

  logout(options?: ReqAccount): Promise<Account>

  scan(_option: {scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean}>;

  accounts(): Promise<Account[]>;
}
