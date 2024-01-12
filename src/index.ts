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

import { CaptureReceipt } from './capture-receipt';

const instance: CaptureReceipt = new CaptureReceipt();

export { instance };
