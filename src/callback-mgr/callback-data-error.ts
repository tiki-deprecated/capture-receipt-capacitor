import { CallbackData } from "./callback-data"
import type { CallbackDataErrorInterface } from "./callback-data-error-if"

import type { PluginEvent } from "src/plugin/plugin-event"

export class CallbackDataError extends CallbackData {

  constructor(
    requestId: string,
    event: PluginEvent,
    payload: CallbackDataErrorInterface,
  ) {
    super(
      requestId,
      event,
      payload,
    )
  }
}