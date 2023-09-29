/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from '../account';
import type { PluginEvent } from '../plugin/plugin-event';
import type { Receipt } from '../receipt';

import type { CallbackError } from './callback-error';
import type { CallbackMgrCall } from './callback-mgr-call';

/**
 * The details of the callback.
 *
 * It is used to keep track of the registered callbacks for each request.
 */
export class CallbackDetails {
  /**
   * A unique identifier of the {@link Req} to which this callback should listen.
   */
  requestId: string;
  /**
   * The {@link PluginEvent} that triggered the callback.
   */
  event: PluginEvent;

  /**
   * The actual callback function. Should be undefined on plugin responses.
   */
  callback: CallbackMgrCall | undefined;

  /**
   * The payload returned. Should be undefined on plugin requests.
   */
  payload: CallbackError | Account | Receipt | undefined;

  /**
   * The unique id of the callback.
   */
  get id(): string {
    return `${this.event.toString()}:${this.requestId}`;
  }

  constructor(
    requestId: string,
    event: PluginEvent,
    callback: CallbackMgrCall | undefined = undefined,
    payload: CallbackError | Account | Receipt | undefined = undefined,
  ) {
    this.requestId = requestId;
    this.event = event;
    this.callback = callback;
    this.payload = payload;
  }
}
