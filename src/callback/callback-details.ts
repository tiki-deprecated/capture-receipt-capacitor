/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { CallbackEvent } from './callback-event';
import type { AccountCallback, CompleteCallback, ReceiptCallback, ErrorCallback } from './types';

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
   * The {@link CallbackEvent} that triggered the callback.
   */
  event: CallbackEvent;

  callback?: AccountCallback | ReceiptCallback | CompleteCallback | ErrorCallback;

  /**
   * The unique id of the callback.
   */
  get id(): string {
    return `${this.event.toString()}:${this.requestId}`;
  }

  constructor(
    requestId: string,
    event: CallbackEvent,
    callback?: AccountCallback | ReceiptCallback | CompleteCallback | ErrorCallback,
  ) {
    this.requestId = requestId;
    this.event = event;
    this.callback = callback;
  }
}
