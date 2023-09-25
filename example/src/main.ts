import './assets/main.css';
import { createApp } from 'vue';
import * as cap from '@mytiki/tiki-receipt-capacitor';
import { registerPlugin } from '@capacitor/core';

import App from '@/app.vue';
import { ReceiptCapture } from '../../src/receipt-capture';
import type { ReceiptCapturePlugin } from '../../src/receipt-capture-plugin';


const plugin: ReceiptCapturePlugin = registerPlugin<ReceiptCapturePlugin>('ReceiptCapture', {
  web: () => import('../../src/receipt-capture-web').then((m) => new m.ReceiptCaptureWeb()),
});

const instance = new ReceiptCapture(plugin);

export const login = async (user: string, pass: string, source: cap.AccountTypeCommom) => {
  await instance.login(user, pass, source).catch((error) => console.log(error))
};
export const accounts = async () => {
  console.log('teste accounts');
  console.log(await instance.accounts());
};
export const scan = async () => {
  console.log('teste scan');
  console.log(await instance.scan('EMAIL'));
};
export const logout = async () => {
  console.log('teste logout');
  console.log(await instance.logout());
};
export const initialize = async () => {
  console.log('teste initialize');
  await instance.initialize(
    'sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/jLI1A3BmOhqNvtZLzShMcb3/OLQLiqgWjuHuFiqGfg4fnAiPtRcc5uRJ6bCBRkg8EsKabMQkEsMOuVjvEOejVD497WkMgobMbk/X+bdfhPPGdcAHWn5Vnz86SmGdHX5xs6RgYe5jmJCSLiPmB7cjWmxY5ihkCG12Q==',
    'wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw==',
    '',
  );
};

createApp(App).mount('#app');
