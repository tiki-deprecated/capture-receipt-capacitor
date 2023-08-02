/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { StringType } from './string-type';

export interface AdditionalLine {
  type?: StringType;
  text?: StringType;
  lineNumber: string;
}
