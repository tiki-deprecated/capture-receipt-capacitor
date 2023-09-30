/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import type { Account } from './account';
import { CallbackManager } from './callback-mgr';
import { CallbackData } from './callback-mgr/callback-data';
import type { CallbackMgrCall } from './callback-mgr/callback-mgr-call';
import type { CaptureReceiptPlugin } from './plugin';
import { PluginEvent } from './plugin/plugin-event';
import { ReqAccount } from './plugin/req/req-account';
import { ReqInitialize } from './plugin/req/req-initialize';
import { ReqScan } from './plugin/req/req-scan';

/**
 * The primary class for interacting with the Plugin.
 */
export class CaptureReceipt {
  private plugin: CaptureReceiptPlugin;
  private callbackMgr: CallbackManager;

  constructor(plugin: CaptureReceiptPlugin) {
    this.plugin = plugin;
    this.callbackMgr = new CallbackManager();
    this.plugin.addListener('onCapturePluginResult', this.callbackMgr.fire);
  }

  /**
   * Initializes the SDK
   * @param licenseKey The license key of the package.
   * @param productKey The product intelligence key of the package.
   * @returns A Promise that resolves to void on completion
   */
  initialize(licenseKey: string, productKey: string): Promise<void> {
    const req: ReqInitialize = new ReqInitialize(licenseKey, productKey);
    return this.plugin.initialize(req);
  }

  /**
   * Connects a Retailer or Gmail account
   * @param account - the credentials of the account and the Account Type, that identifies
   * if it is Gmail or Retailer and which Retailer is.
   * @returns {@link Account} with isVerified .
   */
  async login(account: Account): Promise<Account> {
    const reqAccount = new ReqAccount(account);
    await this.plugin.login(reqAccount);
    account.isVerified = true;
    return account;
  }

  /**
   * Logout an account or flush all the accounts connected
   * @param account - The account to be disconnected, if the parameter its undefined
   * the method will flush all the accounts connected
   */
  async logout(account: Account | undefined = undefined): Promise<void> {
    const reqAccount = account != undefined ? new ReqAccount(account) : undefined;
    this.plugin.logout(reqAccount);
  }

  /**
   * Scan an Gmail/Retailer account for receipts
   * @param onReceipt - A callback that will be fired when the plugin returns a Receipts
   * @param daysCutOff - The total of past days that the scan will search
   * @param onComplete - A callback that will be called when the process finish
   * @param onError - A callback that will be fired when an error happen
   */
  async scan(
    onReceipt: CallbackMgrCall,
    daysCutOff: number | undefined = 7,
    onComplete: CallbackMgrCall | undefined = undefined,
    onError: CallbackMgrCall | undefined = undefined,
  ): Promise<void> {
    const req = new ReqScan(daysCutOff);
    this.callbackMgr.add(req.requestId, PluginEvent.onReceipt, onReceipt);
    this.callbackMgr.add(req.requestId, PluginEvent.onComplete, onComplete);
    this.callbackMgr.add(req.requestId, PluginEvent.onError, onError);
    await this.plugin.scan(req);
  }
  /**
   * Gets all the connected accounts
   * @param onAccount - A callback that will fire when the plugin returns an account.
   * @param onComplete - A callback called after the search for account is finished.
   * @param onError - A callback that will be fired when an error occurs.
   */
  async accounts(
    onAccount: CallbackMgrCall,
    onComplete: CallbackMgrCall | undefined = undefined,
    onError: CallbackMgrCall | undefined = undefined,
  ): Promise<void> {
    const requestId = uuid.v4();
    const req = { requestId };
    this.callbackMgr.add(req.requestId, PluginEvent.onAccount, onAccount);
    this.callbackMgr.add(req.requestId, PluginEvent.onComplete, onComplete);
    this.callbackMgr.add(req.requestId, PluginEvent.onError, onError);
    await this.plugin.accounts(req);
  }
}
