/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
    productKey?: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;

  scan(): Promise<unknown>;

  loginWithEmail(options: {
    username: string;
    password: string;
    provider: string;
  }): Promise<{ username: string; provider: string }>;

  scrapeEmail(): Promise<unknown>;
}
