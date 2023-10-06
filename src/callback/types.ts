/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from '../account';
import type { Receipt } from '../receipt';

import type { CallbackError } from './callback-error';

export type AccountCallback = (payload: Account) => void;
export type ReceiptCallback = (payload: Receipt) => void;
export type ErrorCallback = (payload: CallbackError) => void;
export type CompleteCallback = () => void;
