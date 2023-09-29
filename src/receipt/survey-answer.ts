/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

/**
 * Represents an answer option for a survey question, along with related details.
 */
export interface SurveyAnswer {
  /**
   * The unique identifier for the answer.
   */
  id: number;

  /**
   * The text content of the answer option.
   */
  text?: string;

  /**
   * The index of the next question in the survey, if applicable.
   */
  nextQuestionIndex?: number;
}
