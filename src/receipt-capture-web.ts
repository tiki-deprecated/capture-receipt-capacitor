/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { Receipt } from './receipt';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';

export class ReceiptCaptureWeb extends WebPlugin implements ReceiptCapturePlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  loginWithRetailer(_options: {
    username: string;
    password: string;
    retailer: string;
  }): Promise<{ username: string; retailer: string; isVerified: boolean }> {
    throw new Error('Method not implemented.');
  }

  retailers(): Promise<{ accounts: [{ username: string; retailer: string; isVerified: boolean }] }> {
    throw new Error('Method not implemented.');
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  removeRetailer(_options: {
    username: string;
    retailer: string;
  }): Promise<{ username: string; retailer: string; isVerified: boolean }> {
    throw new Error('Method not implemented.');
  }

  orders(): Promise<{ retailer: string; username: string; scan: Receipt }> {
    throw this.unimplemented('Mobile Only.');
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  async initialize(_options: { licenseKey: string }): Promise<{ isInitialized: boolean; reason?: string }> {
    throw this.unimplemented('Mobile Only.');
  }

  async scan(): Promise<Receipt> {
    throw this.unimplemented('Mobile Only.');
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  loginWithEmail(_options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<{ username: string; provider: string }> {
    throw this.unimplemented('Mobile Only.');
  }

  scrapeEmail(): Promise<{
    login: { username: string; provider: string };
    scans: Receipt[];
  }> {
    throw this.unimplemented('Mobile Only.');
  }

  verifyEmail(): Promise<{
    accounts: { username: string; provider: string; verified: boolean }[];
  }> {
    throw this.unimplemented('Mobile Only.');
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  removeEmail(_options: { username: string; password: string; provider: string }): Promise<{ success: boolean }> {
    throw this.unimplemented('Mobile Only.');
  }
}
