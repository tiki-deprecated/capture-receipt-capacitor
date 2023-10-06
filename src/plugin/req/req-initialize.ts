/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import type { Req } from './req';

/**
 * The initialization request
 */
export class ReqInitialize implements Req {
  requestId: string;
  ios?: string;
  android?: string;
  productKey: string;

  constructor(productKey: string, ios: string | undefined = undefined, android: string | undefined = undefined) {
    this.requestId = uuid.v4();
    this.productKey = productKey;
    this.ios = ios;
    this.android = android;
  }
}
