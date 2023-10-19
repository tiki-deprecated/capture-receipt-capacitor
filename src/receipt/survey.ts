/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { SurveyQuestion } from './survey-question';

/**
 * Represents a survey with relevant details and a list of associated questions.
 */
export interface Survey {
  /**
   * The client-specific user identifier for the survey.
   */
  clientUserId?: string;

  /**
   * The server identifier for the survey.
   */
  serverId: number;

  /**
   * The slug or short identifier for the survey.
   */
  slug?: string;

  /**
   * The value of the reward offered for completing the survey.
   */
  rewardValue?: string;

  /**
   * The start date of the survey.
   */
  startDate?: string;

  /**
   * The end date of the survey.
   */
  endDate?: string;

  /**
   * An array of questions associated with the survey.
   */
  questions: SurveyQuestion[];
}
