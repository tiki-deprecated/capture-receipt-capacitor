/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Callback } from './callback';
import { CallbackData } from './callback-data';

import type { PluginEvent } from 'src/plugin/plugin-event';
import type { PluginResponse } from 'src/plugin/plugin-response';

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
  add(event: PluginEvent, requestId: string, callback: Callback): void {
    const id = CallbackData.genId(event, requestId)
    this.callbacks.set(id, callback);
  };

  /**
   * Remove a callback
   *
   * @param cbDetails Determines the details of the callback
   */
  remove(id: string): void {
    this.callbacks.delete(id);
  };

  /**
   * Executes a callback based on Plugin Response
   *
   * @param cbDetails
   */
  fire(rsp: PluginResponse): void {
    //eslint-disable-next-line
    debugger

    const callbackData = CallbackData.fromPluginRsp(rsp)
    if(!callbackData){
      console.debug(`Invalid Plugin Response data. Skipping callback: ${JSON.stringify(rsp)}`);
      return
    }else{
      const callback = this.callbacks.get(callbackData.id);
      if (!callback) {
        console.debug(`No callback found for: ${JSON.stringify(rsp)}`);
        return
      }else{
        callback.call(callbackData.payload)
        this.remove(callbackData.id);
      }
    }
  }

  private callbacks = new Map<string, Callback>();
}
