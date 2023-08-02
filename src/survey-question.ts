/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { SurveyAnswer } from './survey-answer';

export interface SurveyQuestion {
  myIndex: number;
  lastQuestion: boolean;
  nextQuestionIndex: number;
  serverId: number;
  text?: string;
  type?: string;
  answers?: SurveyAnswer[];
  multipleAnswers: boolean;
  totalNumberOfQuestions: number;
}
