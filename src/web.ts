/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { WebPlugin } from '@capacitor/core';

import type { ReceiptCapturePlugin } from './definitions';

export class ReceiptCaptureWeb
  extends WebPlugin
  implements ReceiptCapturePlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
