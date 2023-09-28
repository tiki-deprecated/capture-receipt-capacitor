/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { Account } from "../account";
import { Receipt } from "../receipt";
import { CallbackError } from "./callback-error";

export type CallbackMgrCall = (payload: CallbackError | Account | Receipt | undefined) => void