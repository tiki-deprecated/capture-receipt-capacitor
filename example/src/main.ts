import './assets/main.css';
import { createApp } from 'vue';
import { ReceiptCapture } from '../../src/receipt-capture';
import type { ReceiptCapturePlugin } from '@/receipt-capture-plugin';
import { registerPlugin } from '@capacitor/core';

import App from '@/app.vue';

const plugin: ReceiptCapturePlugin = registerPlugin<ReceiptCapturePlugin>('ReceiptCapture', {
  web: () => import('../../src/receipt-capture-web').then((m) => new m.ReceiptCaptureWeb()),
});

const instance: ReceiptCapture = new ReceiptCapture(plugin);

export const login = async () => {
  console.log('teste login');
  console.log(await instance.login('user', 'password', 'GMAIL').catch((error) => console.log(error)));
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
    '463504001942-qdl1m1dvvrde81f52j1dcbvbesgpf97l.apps.googleusercontent.com',
  );
};

createApp(App).mount('#app');
