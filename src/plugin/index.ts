/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { ListenerCallback, PluginListenerHandle } from '@capacitor/core';

import type { Account } from '../account';

import type { Req } from './req/req';
import type { ReqAccount } from './req/req-account';
import type { ReqInitialize } from './req/req-initialize';
import type { ReqScan } from './req/req-scan';

export interface CaptureReceiptPlugin {
  /**
   * Initializes the receipt capture plugin with a license key and product intelligence key.
   *
   * @param options {@link ReqInitialize}
   * @throws Error if the initialization fails.
   */
  initialize(request: ReqInitialize): Promise<void>;

  /**
   * Login into a retailer or email account to scan for receipts.
   * @param options
   */
  login(options: ReqAccount): Promise<Account>;

  /**
   * Log out from one or all {@link Account}.
   *
   * @param options ReqAccount
   */
  logout(options?: ReqAccount): Promise<void>;

  /**
   * Scan for receipts.
   */
  scan(options?: ReqScan): Promise<void>;

  /**
   * Retrieves all saved accounts.
   * @param options ReqAccount
   */
  accounts(options?: Req): Promise<void>;

  /**
   * Listen for a JS event.
   *
   * Android and iOS plugins will fire this event to send receipts from native code.
   * The CallbackManager will handle the callbacks.
   *
   * @param eventName
   * @param listenerFunc
   */
  addListener(
    eventName: 'onCapturePluginResult',
    listenerFunc: ListenerCallback,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
