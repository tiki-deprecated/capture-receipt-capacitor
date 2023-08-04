/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { registerPlugin } from '@capacitor/core';

import { AccountProvider } from './account';
import type { Account } from './account';
import type { AdditionalLine } from './additional-line';
import type { Coupon } from './coupon';
import type { FloatType } from './float-type';
import type { PaymentMethod } from './payment-method';
import type { Product } from './product';
import type { Promotion } from './promotion';
import type { Receipt } from './receipt';
import { ReceiptCapture } from './receipt-capture';
import type { ReceiptCapturePlugin } from './receipt-capture-plugin';
import type { Retailer } from './retailer';
import type { Shipment } from './shipment';
import type { StringType } from './string-type';
import type { Survey } from './survey';
import type { SurveyAnswer } from './survey-answer';
import type { SurveyQuestion } from './survey-question';

const plugin: ReceiptCapturePlugin = registerPlugin<ReceiptCapturePlugin>(
  'ReceiptCapture',
  {
    web: () =>
      import('./receipt-capture-web').then(m => new m.ReceiptCaptureWeb()),
  },
);
const instance: ReceiptCapture = new ReceiptCapture(plugin);

export { instance, AccountProvider };
export type {
  AdditionalLine,
  Coupon,
  StringType,
  FloatType,
  Product,
  Promotion,
  PaymentMethod,
  Survey,
  SurveyAnswer,
  SurveyQuestion,
  Shipment,
  Receipt,
  Retailer,
  ReceiptCapture,
  Account,
};
