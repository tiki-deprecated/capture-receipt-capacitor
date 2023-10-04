/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import { type Account, accountTypes } from '../account';
import type { Receipt } from '../receipt';

import type { CallbackBody } from './callback-body';
import { CallbackDetails } from './callback-details';
import { CallbackError } from './callback-error';
import { CallbackEvent, toCallbackEvent } from './callback-event';
import type { AccountCallback, CompleteCallback, ErrorCallback, ReceiptCallback } from './types';

/**
 * The class responsible for handling the asynchronous callbacks between
 * the plugin and the Capacitor code.
 */
export class CallbackManager {
  readonly id: string = uuid.v4();
  private callbacks: Map<string, CallbackDetails> = new Map();

  /**
   * Adds a new callback
   *
   * @param cbDetails Determines the details of the callback
   */
  add(details: CallbackDetails): void {
    this.callbacks.set(details.id, details);
  }

  /**
   * Remove a callback
   *
   * @param cbDetails Determines the details of the callback
   */
  remove(id: string): void {
    this.callbacks.delete(id);
  }

  /**
   * Executes a callback based on Plugin Response
   *
   * @param cbDetails
   */
  fire(body: CallbackBody): void {
    const event = toCallbackEvent(body.event);
    if (!event) {
      console.debug(`Invalid body. Skipping callback: ${JSON.stringify(body)}`);
      return;
    }
    const details = new CallbackDetails(body.requestId, event);
    const registered = this.callbacks.get(details.id);
    if (!registered) {
      console.debug(`No callback registered. Skipping callback: ${JSON.stringify(body)}`);
      return;
    }
    switch (event) {
      case CallbackEvent.onAccount: {
        const accountType = accountTypes.from(body.payload.id);
        if (!accountType) {
          console.debug(`Invalid AccountType. Skipping callback: ${JSON.stringify(body)}`);
          break;
        }
        const account = body.payload as Account;
        account.type = accountType;
        const callback = registered.callback as AccountCallback;
        callback(account);
        break;
      }
      case CallbackEvent.onComplete: {
        const callback = registered.callback as CompleteCallback;
        callback();
        const requestId = body.requestId;
        const callbackEvents = Object.keys(CallbackEvent);
        for (const event of callbackEvents) {
          this.remove(`${event}:${requestId}`);
        }
        break;
      }
      case CallbackEvent.onReceipt: {
        const receipt = body.payload as Receipt;
        const callback = registered.callback as ReceiptCallback;
        callback(receipt);
        break;
      }
      case CallbackEvent.onError: {
        const error = body.payload as CallbackError;
        const callback = registered.callback as ErrorCallback;
        callback(new CallbackError(error.message, error.code));
        break;
      }
    }
  }
}
