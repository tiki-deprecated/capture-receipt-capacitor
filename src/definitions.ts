/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface ReceiptCapturePlugin {
  initialize(options: {
    licenseKey: string;
  }): Promise<{ isInitialized: boolean; reason?: string }>;

  scan(): Promise<unknown>;
}
