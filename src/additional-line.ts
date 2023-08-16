/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { StringType } from './string-type';

/**
 * Represents an additional line of text or information identified by its line number.
 */
export interface AdditionalLine {
  /**
   * The type or category of the additional line.
   */
  type?: StringType;

  /**
   * The text content of the additional line.
   */
  text?: StringType;

  /**
   * The unique identifier or line number associated with the additional line.
   */
  lineNumber: number;
}
