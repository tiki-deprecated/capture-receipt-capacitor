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
import type { Survey } from './survey';

/**
 * Represents a receipt with various attributes and details.
 */
export interface Receipt {
  /**
   * The date associated with the receipt.
   */
  receiptDate?: StringType;

  /**
   * The time associated with the receipt.
   */
  receiptTime?: StringType;

  /**
   * The retailer identifier associated with the receipt.
   */
  retailerId: Retailer;

  /**
   * An array of products listed in the receipt.
   */
  products: Product[];

  /**
   * An array of coupons applied to the receipt.
   */
  coupons: Coupon[];

  /**
   * The total amount of the receipt.
   */
  total?: FloatType;

  /**
   * The tip amount included in the receipt.
   */
  tip?: FloatType;

  /**
   * The subtotal amount of the receipt.
   */
  subtotal?: FloatType;

  /**
   * The taxes amount of the receipt.
   */
  taxes?: FloatType;

  /**
   * The store number associated with the receipt.
   */
  storeNumber?: StringType;

  /**
   * The merchant name associated with the receipt.
   */
  merchantName?: StringType;

  /**
   * The store address associated with the receipt.
   */
  storeAddress?: StringType;

  /**
   * The city of the store associated with the receipt.
   */
  storeCity?: StringType;

  /**
   * The unique Blink Receipt identifier for the receipt.
   */
  blinkReceiptId?: string;

  /**
   * The state of the store associated with the receipt.
   */
  storeState?: StringType;

  /**
   * The ZIP code of the store associated with the receipt.
   */
  storeZip?: StringType;

  /**
   * The country of the store associated with the receipt.
   */
  storeCountry?: StringType;

  /**
   * The phone number of the store associated with the receipt.
   */
  storePhone?: StringType;

  /**
   * The cashier identifier associated with the receipt.
   */
  cashierId?: StringType;

  /**
   * The transaction identifier associated with the receipt.
   */
  transactionId?: StringType;

  /**
   * The register identifier associated with the receipt.
   */
  registerId?: StringType;

  /**
   * An array of payment methods used in the receipt.
   */
  paymentMethods?: PaymentMethod[];

  /**
   * The tax identifier associated with the receipt.
   */
  taxId?: StringType;

  /**
   * The name of the mall where the transaction occurred.
   */
  mallName?: StringType;

  /**
   * The last 4 digits of the credit card used for payment.
   */
  last4cc?: StringType;

  /**
   * The OCR (Optical Character Recognition) confidence level for the receipt.
   */
  ocrConfidence: number;

  /**
   * The source of the merchant's data in the receipt.
   */
  merchantSource?: string;

  /**
   * Indicates if the top edge of the receipt was found during scanning.
   */
  foundTopEdge: boolean;

  /**
   * Indicates if the bottom edge of the receipt was found during scanning.
   */
  foundBottomEdge: boolean;

  /**
   * The order number associated with the e-receipt.
   */
  eReceiptOrderNumber?: string;

  /**
   * The status of the e-receipt order.
   */
  eReceiptOrderStatus?: string;

  /**
   * The raw HTML content of the e-receipt.
   */
  eReceiptRawHtml?: string;

  /**
   * The shipping address of the e-receipt.
   */
  eReceiptShippingAddress?: string;

  /**
   * An array of shipments associated with the receipt.
   */
  shipments?: Shipment[];

  /**
   * The long transaction identifier associated with the receipt.
   */
  longTransactionId?: StringType;

  /**
   * Indicates whether the receipt's subtotal matches the sum of its products' prices.
   */
  subtotalMatches: boolean;

  /**
   * The email provider for the e-receipt.
   */
  eReceiptEmailProvider?: string;

  /**
   * The email identifier associated with the e-receipt.
   */
  eReceiptEmailId?: string;

  /**
   * Indicates if the e-receipt is authenticated.
   */
  eReceiptAuthenticated: boolean;

  /**
   * Indicates if the shopper was an Instacart shopper.
   */
  instacartShopper: boolean;

  /**
   * Indicates if the receipt is an e-receipt.
   */
  eReceipt: boolean;

  /**
   * An array of e-receipts received via email.
   */
  eReceiptComponentEmails?: Receipt[];

  /**
   * Indicates if the receipt is a duplicate.
   */
  duplicate: boolean;

  /**
   * Indicates if the receipt is fraudulent.
   */
  fraudulent: boolean;

  /**
   * The date and time of the receipt as a Unix timestamp.
   */
  receiptDateTime: number;

  /**
   * An array of duplicate Blink Receipt identifiers.
   */
  duplicateBlinkReceiptIds: string[];

  /**
   * A guessed merchant match based on provided data.
   */
  merchantMatchGuess?: StringType;

  /**
   * The number of products pending lookup.
   */
  productsPendingLookup: number;

  /**
   * An array of qualified promotions associated with the receipt.
   */
  qualifiedPromotions: Promotion[];

  /**
   * An array of unqualified promotions associated with the receipt.
   */
  unqualifiedPromotions: Promotion[];

  /**
   * A map of extended fields associated with the receipt.
   */
  extendedFields: Map<string, string>;

  /**
   * A map of additional fees associated with the e-receipt.
   */
  eReceiptAdditionalFees: Map<string, string>;

  /**
   * The type of purchase (e.g., online, in-store) associated with the receipt.
   */
  purchaseType?: StringType;

  /**
   * Indicates if the receipt is part of a loyalty program.
   */
  loyaltyForBanner: boolean;

  /**
   * The channel through which the receipt was obtained (e.g., email, app).
   */
  channel?: StringType;

  /**
   * The submission date of the receipt as a Unix timestamp.
   */
  submissionDate?: number;

  /**
   * The entity that fulfilled the e-receipt (e.g., the store, a third-party).
   */
  eReceiptFulfilledBy?: string;

  /**
   * The shipping status of the e-receipt.
   */
  eReceiptShippingStatus?: string;

  /**
   * The point of sale (POS) system used for the e-receipt.
   */
  eReceiptPOSSystem?: string;

  /**
   * The sub-merchant of the e-receipt.
   */
  eReceiptSubMerchant?: string;

  /**
   * An array of qualified surveys associated with the receipt.
   */
  qualifiedSurveys?: Survey[];

  /**
   * The barcode associated with the receipt.
   */
  barcode?: string;

  /**
   * The merchant's email address associated with the e-receipt.
   */
  eReceiptMerchantEmail?: string;

  /**
   * The subject of the e-receipt email.
   */
  eReceiptEmailSubject?: string;

  /**
   * The shipping costs associated with the e-receipt.
   */
  eReceiptShippingCosts?: number;

  /**
   * The currency code associated with the receipt.
   */
  currencyCode?: string;

  /**
   * The name of the merchant as known by the client.
   */
  clientMerchantName?: string;

  /**
   * Indicates if the receipt is part of a loyalty program.
   */
  loyaltyProgram: boolean;

  /**
   * An array of merchant sources associated with the receipt.
   */
  merchantSources?: number[];

  /**
   * The ID of the payment terminal used for the transaction.
   */
  paymentTerminalId?: StringType;

  /**
   * The transaction ID associated with the payment.
   */
  paymentTransactionId?: StringType;

  /**
   * The combined raw text content of the receipt.
   */
  combinedRawText?: StringType;
}
