import { Account } from "../../account";
import { Receipt } from "../../receipt";
import { CaptureReceiptEvent } from '../plugin-event'
import { Capture }

export interface Rsp {
  requestId: string,
  event: CaptureReceiptEvent
  payload: CaptureReceiptError | Account | Receipt
}