/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Receipt } from './receipt';

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
    productKey?: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;

  scan(): Promise<Receipt>;

  loginWithEmail(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<{ username: string; provider: string }>;

  scrapeEmail(): Promise<{
    login: { username: string; provider: string };
    scans: Receipt[];
  }>;

  verifyEmail(): Promise<{
    accounts: { username: string; provider: string; verified: boolean }[];
  }>;

  removeEmail(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<{ success: boolean }>;
}
