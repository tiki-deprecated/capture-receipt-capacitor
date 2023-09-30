/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from '../account';
import { PluginEvent } from '../plugin/plugin-event';
import type { Receipt } from '../receipt';

import { CallbackDataAccount } from './callback-data-account';
import { CallbackDataError } from './callback-data-error';
import type { CallbackDataErrorInterface } from './callback-data-error-if';
import { CallbackReceipt } from './callback-receipt';

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
  payload: CallbackDataErrorInterface | Account | Receipt | undefined;

  /**
   * The unique id of the callback.
   */
  get id(): string {
    return CallbackData.genId(this.event, this.requestId)
  }

  constructor(
    requestId: string,
    event: PluginEvent,
    payload?: CallbackDataErrorInterface | Account | Receipt,
  ) {
    this.requestId = requestId;
    this.event = event;
    this.payload = payload;
  }

  public static fromPluginRsp(rsp: PluginResponse): CallbackDataAccount | CallbackReceipt | CallbackData | CallbackDataError| undefined {
    try{
      const event = rsp.event
      switch (event) {
        case PluginEvent.onAccount:
          return new CallbackDataAccount(
            rsp.requestId,
            rsp.event,
            rsp.payload as Account
          )
        case PluginEvent.onReceipt:
          return new CallbackReceipt(
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
          return new CallbackDataError(
            rsp.requestId,
            rsp.event,
            rsp.payload as CallbackDataErrorInterface
          )
        default:
          return undefined
      }
    }catch(e){
      return undefined
    }
  }

  public static genId(event: PluginEvent, requestId: string): string {
    return `${event.toString()}:${requestId}`;
  }
}
