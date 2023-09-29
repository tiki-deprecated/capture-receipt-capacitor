/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { Account } from '../account';

import type { CaptureReceiptPlugin } from '.';

export class PluginWeb extends WebPlugin implements CaptureReceiptPlugin {
  async initialize(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }

  async login(): Promise<Account> {
    throw this.unimplemented('Mobile Only');
  }

  async logout(): Promise<void> {
    throw this.unimplemented('Mobile Only');
  }

  async scan(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }

  async accounts(): Promise<void> {
    throw this.unimplemented('Mobile Only.');
  }
}
