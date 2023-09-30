/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * The representation of an Error that happened in the plugin.
 */
export interface CallbackDataError {
  message: string;
  code?: number;
}
