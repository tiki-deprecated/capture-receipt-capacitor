/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { FloatType } from './float-type';
import type { StringType } from './string-type';

export interface PaymentMethod {
  paymentMethod?: StringType;
  cardType?: StringType;
  cardIssuer?: StringType;
  amount?: FloatType;
}
