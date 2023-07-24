/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { ReceiptCapturePlugin } from './definitions';

export class ReceiptCaptureWeb
  extends WebPlugin
  implements ReceiptCapturePlugin
{
  async initialize(_options: {
    licenseKey: string;
  }): Promise<{ isInitialized: boolean; reason?: string }> {
    throw this.unimplemented('Mobile Only.');
  }

  async scan(): Promise<unknown> {
    throw this.unimplemented('Mobile Only.');
  }

  async testCreds(): Promise<unknown> {
    throw this.unimplemented('Mobile Only.');
  }
}
