import type { PluginEvent } from "./plugin-event";

import type { Account } from "src/account";
import type { Receipt } from "src/receipt";
import type { CallbackDataError } from "src/types";


export interface PluginResponse {
  requestId: string,
  event: PluginEvent,
  payload?: Account | Receipt | CallbackDataError
}