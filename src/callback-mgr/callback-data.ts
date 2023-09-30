/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from '../account';
import { PluginEvent } from '../plugin/plugin-event';
import type { Receipt } from '../receipt';

import type { CallbackDataError } from './callback-data-error';

import type { PluginResponse } from 'src/plugin/plugin-response';

/**
 * The details of the callback.
 *
 * It is used to keep track of the registered callbacks for each request.
 */
export class CallbackData {
  /**
   * A unique identifier of the {@link Req} to which this callback should listen.
   */
  requestId: string;

  /**
   * The {@link PluginEvent} that triggered the callback.
   */
  event: PluginEvent;

  /**
   * The payload returned. Should be undefined on plugin requests.
   */
  payload: CallbackDataError | Account | Receipt | undefined;

  /**
   * The unique id of the callback.
   */
  get id(): string {
    return CallbackData.genId(this.event, this.requestId)
  }

  constructor(
    requestId: string,
    event: PluginEvent,
    payload?: CallbackDataError | Account | Receipt,
  ) {
    this.requestId = requestId;
    this.event = event;
    this.payload = payload;
  }

  public static fromPluginRsp(rsp: PluginResponse): CallbackData | undefined {
    try {
      const event = rsp.event
      switch (event) {
        case PluginEvent.onAccount:
          return new CallbackData(
            rsp.requestId,
            rsp.event,
            rsp.payload as Account
          )
        case PluginEvent.onReceipt:
          return new CallbackData(
            rsp.requestId,
            rsp.event,
            rsp.payload as Receipt
          )
        case PluginEvent.onComplete:
          return new CallbackData(
            rsp.requestId,
            rsp.event,
          )
        case PluginEvent.onError:
          return new CallbackData(
            rsp.requestId,
            rsp.event,
            rsp.payload as CallbackDataError
          )
        default:
          return undefined
      }
    } catch (e) {
      return undefined
    }
  }

  public static genId(event: PluginEvent, requestId: string): string {
    return `${event.toString()}:${requestId}`;
  }
}
