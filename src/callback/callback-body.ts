/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface CallbackBody {
  requestId: string;
  event: string;
  payload: any;
}
