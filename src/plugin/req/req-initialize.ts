/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Req } from './req'
import * as uuid from 'uuid';

/**
 * The initialization request
 */
export class ReqInitialize implements Req {
  requestId: string
  licenseKey: string
  productKey: string
  
  constructor(  
    licenseKey: string,
    productKey: string )
  {
    this.requestId = uuid.v4()
    this.licenseKey = licenseKey
    this.productKey = productKey
  }
}