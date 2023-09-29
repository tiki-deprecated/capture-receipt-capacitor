/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import type { Account } from '../../account';

import type { Req } from './req';

/**
 * A scan request to the plugin
 */
export class ReqScan implements Req {
  requestId: string;
  dayCutOff?: number;
  account?: Account | undefined;

  constructor(dayCutOff?: number, account?: Account | undefined) {
    this.requestId = uuid.v4();
    this.dayCutOff = dayCutOff;
    this.account = account;
  }
}