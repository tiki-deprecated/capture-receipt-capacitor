import type { Account } from "src/account";
import type { Receipt } from "src/receipt";
import type { CallbackError } from "src/types";

export interface PluginResponse {
  requestId: string,
  payload?: Account | Receipt | CallbackError
}