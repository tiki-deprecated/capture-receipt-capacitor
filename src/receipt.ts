/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import type { Coupon } from './coupon';
import type { FloatType } from './float-type';
import type { PaymentMethod } from './payment-method';
import type { Product } from './product';
import type { Promotion } from './promotion';
import type { Retailer } from './retailer';
import type { Shipment } from './shipment';
import type { StringType } from './string-type';
import type { SurveyQuestion } from './survey-question';

export interface Receipt {
  receiptDate?: StringType;
  receiptTime?: StringType;
  retailerId: Retailer;
  products: Product[];
  coupons: Coupon[];
  total?: FloatType;
  tip?: FloatType;
  subtotal?: FloatType;
  taxes?: FloatType;
  storeNumber?: StringType;
  merchantName?: StringType;
  storeAddress?: StringType;
  storeCity?: StringType;
  blinkReceiptId?: string;
  storeState?: StringType;
  storeZip?: StringType;
  storeCountry?: StringType;
  storePhone?: StringType;
  cashierId?: StringType;
  transactionId?: StringType;
  registerId?: StringType;
  paymentMethods?: PaymentMethod[];
  taxId?: StringType;
  mallName?: StringType;
  last4cc?: StringType;
  ocrConfidence: number;
  merchantSource?: string;
  foundTopEdge: boolean;
  foundBottomEdge: boolean;
  eReceiptOrderNumber?: string;
  eReceiptOrderStatus?: string;
  eReceiptRawHtml?: string;
  eReceiptShippingAddress?: string;
  shipments?: Shipment[];
  longTransactionId?: StringType;
  subtotalMatches: boolean;
  eReceiptEmailProvider?: string;
  eReceiptEmailId?: string;
  eReceiptAuthenticated: boolean;
  instacartShopper: boolean;
  eReceipt: boolean;
  eReceiptComponentEmails?: Receipt[];
  duplicate: boolean;
  fraudulent: boolean;
  receiptDateTime: number;
  duplicateBlinkReceiptIds: string[];
  merchantMatchGuess?: StringType;
  productsPendingLookup: number;
  qualifiedPromotions: Promotion[];
  unqualifiedPromotions: Promotion[];
  extendedFields: Map<string, string>;
  eReceiptAdditionalFees: Map<string, string>;
  purchaseType?: StringType;
  loyaltyForBanner: boolean;
  channel?: StringType;
  submissionDate?: number;
  eReceiptFulfilledBy?: string;
  eReceiptShippingStatus?: string;
  eReceiptPOSSystem?: string;
  eReceiptSubMerchant?: string;
  qualifiedSurveys?: SurveyQuestion[];
  barcode?: string;
  eReceiptMerchantEmail?: string;
  eReceiptEmailSubject?: string;
  eReceiptShippingCosts?: number;
  currencyCode?: string;
  clientMerchantName?: string;
  loyaltyProgram: boolean;
  merchantSources?: number[];
  paymentTerminalId?: StringType;
  paymentTransactionId?: StringType;
  combinedRawText?: StringType;
}
