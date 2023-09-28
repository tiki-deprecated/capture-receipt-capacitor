/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { FloatType } from './float-type';
import type { StringType } from '../string-type';

/**
 * Represents a payment method along with relevant details.
 */
export interface PaymentMethod {
  /**
   * The type of payment method used for the transaction.
   */
  paymentMethod?: StringType;

  /**
   * The type of card used for payment, if applicable.
   */
  cardType?: StringType;

  /**
   * The issuer of the card, if applicable.
   */
  cardIssuer?: StringType;

  /**
   * The amount associated with the payment method. This includes the value and confidence level.
   */
  amount?: FloatType;
}
