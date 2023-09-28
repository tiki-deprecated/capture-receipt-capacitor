/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from '../../account';
import { Req } from './req'
import * as uuid from 'uuid';

/**
 * Am account request sent to the plugin.
 */
export class ReqAccount implements Req {
  requestId: string;
  username?: string;
  password?: string;
  source?: string;

  constructor( account: Account ){
    this.requestId = uuid.v4()
    this.username = account.username
    this.password = account.password
    this.source = account.type.source
  }
}