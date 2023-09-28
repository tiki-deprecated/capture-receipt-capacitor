/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';
import type { CaptureReceiptPlugin } from '.';
import type { Account } from '../account';
import { ReqScan } from './req/req-scan';

export class PluginWeb extends WebPlugin implements CaptureReceiptPlugin {
  async initialize() {
    throw this.unimplemented('Mobile Only.');
  }
  
  async login(): Promise<Account> {
    throw this.unimplemented('Mobile Only')
  }
  
  async logout(): Promise<void> {
    throw this.unimplemented('Mobile Only')
  }

  async scan(_options: ReqScan): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }

  async accounts(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  };
}
