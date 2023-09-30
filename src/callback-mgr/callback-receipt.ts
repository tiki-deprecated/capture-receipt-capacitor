import { CallbackData } from "./callback-data";

import type { PluginEvent } from "src/plugin/plugin-event";
import type { Receipt } from "src/receipt";

export class CallbackReceipt extends CallbackData {

  constructor(
    requestId: string,
    event: PluginEvent,
    payload: Receipt,
  ) {
    super(
      requestId,
      event,
      payload,
    )
  }
}