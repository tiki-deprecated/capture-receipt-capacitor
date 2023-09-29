/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents a type that holds information about a string value
 * along with its associated confidence level.
 */
export interface StringType {
  /**
   * The confidence level associated with the string value.
   * This value indicates the certainty or reliability of the reported value.
   */
  confidence: number;

  /**
   * The actual string value.
   */
  value?: string;
}
