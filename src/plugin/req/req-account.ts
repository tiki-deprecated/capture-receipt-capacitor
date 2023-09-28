/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import * as uuid from 'uuid';

import type { Account } from '../../account';

import type { Req } from './req';

/**
 * Am account request sent to the plugin.
 */
export class ReqAccount implements Req {
  requestId: string;
  username?: string;
  password?: string;
  source?: string;

  constructor(account: Account) {
    this.requestId = uuid.v4();
    this.username = account.username;
    this.password = account.password;
    this.source = account.type.id;
  }
}
