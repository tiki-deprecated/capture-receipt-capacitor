/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { SurveyAnswer } from './survey-answer';
import type { SurveyResponse } from './survey-response';

/**
 * Represents a survey question along with relevant details and potential answers.
 */
export interface SurveyQuestion {
  /**
   * The index of the question within the survey.
   */
  myIndex: number;

  /**
   * Indicates if the question is the last question in the survey.
   */
  lastQuestion: boolean;

  /**
   * The index of the next question in the survey.
   */
  nextQuestionIndex: number;

  /**
   * The server identifier for the question.
   */
  serverId: number;

  /**
   * The text of the question.
   */
  text?: string;

  /**
   * The type or category of the question.
   */
  type?: string;

  /**
   * An array of potential answers associated with the question.
   */
  answers?: SurveyAnswer[];

  /**
   * Indicates if the question allows multiple answers.
   */
  multipleAnswers: boolean;

  /**
   * The total number of questions in the survey.
   */
  totalNumberOfQuestions: number;

  /**
   * The user's response to the survey.
   */
  userResponse?: SurveyResponse;
}
