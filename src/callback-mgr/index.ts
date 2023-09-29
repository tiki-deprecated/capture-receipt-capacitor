/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { toPluginEvent } from '../plugin/plugin-event';

import { CallbackDetails } from './callback-details';
/**
 * The class responsible for handling the asynchronous callbacks between
 * the plugin and the Capacitor code.
 */
export class CallbackManager {
  /**
   * Adds a new callback
   *
   * @param cbDetails Determines the details of the callback
   */
  add = (cbDetails: CallbackDetails): void => {
    this.callbacks.set(cbDetails.id, cbDetails);
  };

  /**
   * Remove a callback
   *
   * @param cbDetails Determines the details of the callback
   */
  remove = (cbDetails: CallbackDetails): void => {
    this.callbacks.delete(cbDetails.id);
  };

  /**
   * Executes a callback
   *
   * @param cbDetails
   */
  fire = (err: any, ..._args: any[]): void => {
    const event = toPluginEvent(err.event);
    if (event === undefined) {
      console.debug(`Invalid event. Skipping callback: ${JSON.stringify(err)}`);
      return;
    } else {
      const details = new CallbackDetails(err.requestId, event);
      const cb = this.callbacks.get(details.id);
      if (cb) {
        const call = cb.callback;
        const payload = details.payload;
        if (call) call(payload);
        this.remove(details);
      }
    }
  };

  private callbacks = new Map<string, CallbackDetails>();
}
