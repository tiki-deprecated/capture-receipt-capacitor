/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import './assets/main.css';
import type { Account, CallbackError, Receipt } from '@mytiki/capture-receipt-capacitor';
import { accountTypes, instance } from '@mytiki/capture-receipt-capacitor';
import { createApp } from 'vue';

import App from './app.vue';

export const login = async (username: string, password: string, source: string) => {
  const account: Account = {
    username,
    password,
    type: accountTypes.from(source)!,
  };
  console.log('login method:');
  console.log(await instance.login(account));
};

export const accounts = async (): Promise<void> => {
  console.log('account method:');
  instance.accounts(
    (account: Account) => {
      console.log('onAccount:');
      console.log(account);
    },
    () => console.log('complete'),
    (payload: CallbackError) => console.log('error', JSON.stringify(payload)),
  );
};

export const scan = async (): Promise<void> => {
  console.log('scan method');
  instance.scan(
    (receipt: Receipt) => console.log(receipt),
    7,
    () => console.log('complete'),
    (payload: CallbackError) => console.log('error', JSON.stringify(payload)),
  );
};
export const logout = async (): Promise<void> => instance.logout();
export const initialize = async (): Promise<void> => {
  await instance.initialize(
    'sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/jLI1A3BmOhqNvtZLzShMcb3/OLQLiqgWjuHuFiqGfg4fnAiPtRcc5uRJ6bCBRkg8EsKabMQkEsMOuVjvEOejVD497WkMgobMbk/X+bdfhPPGdcAHWn5Vnz86SmGdHX5xs6RgYe5jmJCSLiPmB7cjWmxY5ihkCG12Q==',
    'wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw==',
  );
};

createApp(App).mount('#app');
