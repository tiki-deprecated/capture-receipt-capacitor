/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface Account {
  username: string;
  provider?: AccountProvider;
  verified?: boolean;
}

export enum AccountProvider {
  GMAIL = 'GMAIL',
}

export const providers: Map<string, AccountProvider> = new Map(
  Object.values(AccountProvider).map(value => [`${value}`, value] as const),
);
