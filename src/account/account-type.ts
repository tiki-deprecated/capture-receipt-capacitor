/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface AccountType {
  id: string;
  name: string;
  type: 'EMAIL' | 'RETAILER';
  icon: string;
}
