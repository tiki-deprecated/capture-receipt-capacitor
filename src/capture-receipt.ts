/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from "./account";
import { CaptureReceiptPlugin } from "./plugin";
import { ReqInitialize } from "./plugin/req/req-initialize";
import { Receipt } from './receipt';
import { Rsp } from './plugin/rsp/rsp';
import { CaptureReceiptEvent } from "./plugin/plugin-event";
import { CaptureReceiptError } from "./plugin/plugin-error";
import { v4 as uuidv4 } from 'uuid';


/**
 * The primary class for interacting with the Plugin.
 */
export class CaptureReceipt {
  private plugin: CaptureReceiptPlugin;
  private callbacks: Map<String, ((payload: Account | Receipt) => void)> = new Map()

  private errorCallbacks: Map<String, ((payload: CaptureReceiptError) => void)> = new Map()

  constructor(plugin: CaptureReceiptPlugin) {
    this.plugin = plugin;
    // this.plugin.addListener('CaptureReceiptEvent', this.onListener)
    // call the manager on event and fire
  }

  /**
   * 
   * @param licenseKey 
   * @param productKey 
   * @returns 
   */
  initialize(licenseKey: string, productKey: string): Promise<void> {
    const req: ReqInitialize = {
      licenseKey: licenseKey,
      productKey: productKey
    };
    return this.plugin.initialize(req).catch((error) => {
      throw Error(error);
    });

  };

  /**
   * Login a new account to be used in {@link scan}
   * 
   * @param username 
   * @param password 
   * @param accountType - The account type to be looged in. See {@link accountType}
   * @param onLogin - The login callback. See {@link OnLogin}
   */
  login(account: Account): Promise<Account> {
    // call manager and add callback
    this.plugin.login({ username, password });
  }

  /**
   * Log out from one or all {@link Account}.
   *
   * Don't provide an Account to logout from all Accounts.
   * 
   * @param account 
   */
  logout(account: Account | undefined = undefined): Promise<void> {
    this.plugin.logout({ username: username, password: password, source: source });
  }


  scan(
    onReceipt: (receipt: Receipt) => void,
    options: ReqScanOptions = {
      dayCutOff: 7
    },
    onComplete: (() => void) | undefined = undefined,
    onError: ((message: String, error: ReceiptCaptureError) => void) | undefined = undefined
  ) {

  }
  /**
   * Retrieves all saved email and accounts.
   *
   * @returns - an array of Accounts.
   */


  accounts(
    onAccount: (account: Account) => void,
    onComplete?: () => void | undefined = undefined,
    onError?: (message: String, error: ReceiptCaptureError) => void | undefined = undefined
  ) {
    this.plugin.accounts();
  }

  private onListener(rsp: Rsp) {
    const callback = this.callbacks.get(rsp.requestId)
    const errorCallback = this.errorCallbacks.get(rsp.requestId)
    const onCompleteCallback
    switch (rsp.event) {
      case CaptureReceiptEvent.onAccount:
      case CaptureReceiptEvent.onReceipt:
        if (callback) {
          callback(rsp.payload)
        }
        break;
      case CaptureReceiptEvent.onError:
        if (errorCallback) {
          errorCallback(rsp.payload)
        }
        break;
    }
  }

  private generateId(input: string) {

  }
}
