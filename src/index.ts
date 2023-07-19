/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { registerPlugin } from '@capacitor/core';

import type { ReceiptCapturePlugin } from './definitions';

const ReceiptCapture = registerPlugin<ReceiptCapturePlugin>('ReceiptCapture', {
  web: () => import('./web').then(m => new m.ReceiptCaptureWeb()),
});

export * from './definitions';
export { ReceiptCapture };
