/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents a type that holds information about a floating-point value
 * along with its associated confidence level.
 */
export interface FloatType {
  /**
   * The confidence level associated with the floating-point value, if available.
   * This value indicates the certainty or reliability of the reported value.
   */
  confidence?: number;

  /**
   * The actual floating-point value.
   */
  value: number;
}
