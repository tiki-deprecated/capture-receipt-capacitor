/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * The representation of an Error that happened in the plugin.
 */
export interface CallbackError {
  message: string;
  code?: number;
}
