/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * @file Entry point module for the Plugin.
 *
 * @module tiki-capture-receipt-capacitor
 * @license MIT
 */
import { registerPlugin } from '@capacitor/core';

import { CaptureReceiptPlugin } from './plugin';
import { CaptureReceipt } from './capture-receipt';

/**
 * Registers the {@link CaptureReceiptPlugin} with Capacitor.
 */
const plugin: CaptureReceiptPlugin = registerPlugin<CaptureReceiptPlugin>('CaptureReceipt', {
  web: () => import('./plugin/plugin-web').then((m) => new m.CaptureReceiptWeb()),
});

/**
 * Creates a singleton instance of the {@link CaptureReceipt} using the registered
 * plugin.
 */
const instance: CaptureReceipt = new CaptureReceipt(plugin);

export { instance };
export type { CaptureReceipt };
export type * from './types';