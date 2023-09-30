import { CallbackData } from "./callback-data";

import type { Account } from "src/account";
import type { PluginEvent } from "src/plugin/plugin-event";

export class CallbackDataAccount extends CallbackData {

  constructor(
    requestId: string,
    event: PluginEvent,
    payload: Account,
  ) {
    super(
      requestId,
      event,
      payload,
    )
  }
}



