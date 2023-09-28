/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from "./account";
import { CaptureReceiptPlugin } from "./plugin";
import { ReqInitialize } from "./plugin/req/req-initialize";
import { CallbackManager } from "./callback-mgr";
import { ReqAccount } from "./plugin/req/req-account";
import { ReqScan } from "./plugin/req/req-scan";
import { CallbackDetails } from "./callback-mgr/callback-details";
import { PluginEvent } from "./plugin/plugin-event";
import { CallbackMgrCall } from "./callback-mgr/callback-mgr-call";
import * as uuid from "uuid"


/**
 * The primary class for interacting with the Plugin.
 */
export class CaptureReceipt {
  private plugin: CaptureReceiptPlugin;
  private callbackMgr: CallbackManager

  constructor(plugin: CaptureReceiptPlugin) {
    this.plugin = plugin;
    this.callbackMgr = new CallbackManager()
    this.plugin.addListener("onCapturePluginResult", this.callbackMgr.fire)
  }

  /**
   * Initializes the SDK
   * 
   * @param licenseKey The license key of the package.
   * @param productKey The product intelligence key of the package.
   * @returns A Promise that resolves to void on completion
   */
  initialize(licenseKey: string, productKey: string): Promise<void> {
    const req: ReqInitialize = new ReqInitialize(
      licenseKey,
      productKey
    );
    return this.plugin.initialize(req)
  };

  /**
   * 
   * @param account 
   * @returns {@link Account} with isVerified . 
   */
  async login(account: Account): Promise<Account> {
    const reqAccount = new ReqAccount(account)
    await this.plugin.login(reqAccount)
    account.isVerified = true
    return account
  }

  async logout(account: Account | undefined = undefined): Promise<void> {
    const reqAccount = account != undefined ? new ReqAccount(account) : undefined
    this.plugin.logout(reqAccount);
  }

  scan(
    onReceipt: CallbackMgrCall,
    daysCutOff: number | undefined = 7,
    onComplete: CallbackMgrCall | undefined = undefined,
    onError: CallbackMgrCall | undefined = undefined
  ) {
    const req = new ReqScan(daysCutOff)
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onReceipt, onReceipt))
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onComplete, onComplete))
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onError, onError))
    this.plugin.scan(req)
  }

  accounts(
    onAccount: CallbackMgrCall,
    onComplete: CallbackMgrCall | undefined = undefined,
    onError: CallbackMgrCall | undefined = undefined
  ) {
    const requestId = uuid.v4()
    const req = {requestId}
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onAccount, onAccount))
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onComplete, onComplete))
    this.callbackMgr.add(new CallbackDetails(req.requestId, PluginEvent.onError, onError))
    this.plugin.scan(req)
  }
}