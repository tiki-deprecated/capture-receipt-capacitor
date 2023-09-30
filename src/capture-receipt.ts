/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import type { Account } from './account';
import { CallbackDetails } from './callback/callback-details';
import { CallbackEvent } from './callback/callback-event';
import { CallbackManager } from './callback/callback-mgr';
import type { AccountCallback, CompleteCallback, ErrorCallback, ReceiptCallback } from './callback/types';
import type { CaptureReceiptPlugin } from './plugin';
import { ReqAccount } from './plugin/req/req-account';
import { ReqInitialize } from './plugin/req/req-initialize';
import { ReqScan } from './plugin/req/req-scan';

/**
 * The primary class for interacting with the Plugin.
 */
export class CaptureReceipt {
  private plugin: CaptureReceiptPlugin;
  private callbackMgr: CallbackManager = new CallbackManager();

  constructor(plugin: CaptureReceiptPlugin) {
    this.plugin = plugin;
    this.plugin.addListener('onCapturePluginResult', (err: any, ..._args: any[]) => {
      this.callbackMgr.fire(err);
    });
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
    await this.plugin.logout(reqAccount);
  }

  /**
   * Scan an Gmail/Retailer account for receipts
   * @param onReceipt - A callback that will be fired when the plugin returns a Receipts
   * @param daysCutOff - The total of past days that the scan will search
   * @param onComplete - A callback that will be called when the process finish
   * @param onError - A callback that will be fired when an error happen
   */
  async scan(
    onReceipt: ReceiptCallback,
    daysCutOff: number | undefined = 7,
    onComplete?: CompleteCallback,
    onError?: ErrorCallback,
  ): Promise<void> {
    const req = new ReqScan(daysCutOff);
    this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onReceipt, onReceipt));
    if (onComplete) this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onComplete, onComplete));
    if (onError) this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onError, onError));
    await this.plugin.scan(req);
  }
  /**
   * Gets all the connected accounts
   * @param onAccount - A callback that will fire when the plugin returns an account.
   * @param onComplete - A callback called after the search for account is finished.
   * @param onError - A callback that will be fired when an error occurs.
   */
  async accounts(onAccount: AccountCallback, onComplete?: CompleteCallback, onError?: ErrorCallback): Promise<void> {
    const requestId = uuid.v4();
    const req = { requestId };
    this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onAccount, onAccount));
    if (onComplete) this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onComplete, onComplete));
    if (onError) this.callbackMgr.add(new CallbackDetails(req.requestId, CallbackEvent.onError, onError));
    await this.plugin.accounts(req);
  }
}
