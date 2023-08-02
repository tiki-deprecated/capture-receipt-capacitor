/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { SurveyQuestion } from './survey-question';

export interface Survey {
  clientUserId?: string;
  serverId: number;
  slug?: string;
  rewardValue?: string;
  startDate?: string;
  endDate?: string;
  questions?: SurveyQuestion[];
}
