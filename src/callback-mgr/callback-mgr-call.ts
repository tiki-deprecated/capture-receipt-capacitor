/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Account } from '../account';
import type { Receipt } from '../receipt';

import type { CallbackError } from './callback-error';

export type CallbackMgrCall = (payload: CallbackError | Account | Receipt | undefined) => void;
