/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents a user's survey response details.
 */
export interface SurveyResponse {
  /**
   * The indices of selected answers in the survey.
   */
  answersSelected: number[];
  /**
   * Free-text comments provided as part of the survey response.
   */
  freeText?: string;
}
