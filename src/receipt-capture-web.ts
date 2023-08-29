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
  async initialize(_options: { licenseKey: string }): Promise<{ isInitialized: boolean; reason?: string }> {
    throw this.unimplemented('Mobile Only.');
  }

  async login(): Promise<Account>{
    throw this.unimplemented('Mobile Only')
  }

  async logout(): Promise<Account>{
    throw this.unimplemented('Mobile Only')
  }

  async scan(_options:{scanType: ScanType | undefined, account?: Account}): Promise<{receipt: Receipt, isRunning: boolean}> {
    throw this.unimplemented('Mobile Only.');
  }

  async accounts(): Promise<Account[]>{
    throw this.unimplemented('Mobile Only.');
  };
}
