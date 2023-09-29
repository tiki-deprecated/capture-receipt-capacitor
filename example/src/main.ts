import './assets/main.css';
import { createApp } from 'vue';
import { CaptureReceipt } from '../../src/capture-receipt';
import { type Account } from '../../src/account'
import type { CaptureReceiptPlugin } from '../../src/plugin';
import { registerPlugin } from '@capacitor/core';
import App from '@/app.vue';
import { accountTypes } from 'dist/types';

const plugin: CaptureReceiptPlugin = registerPlugin<CaptureReceipt>('CaptureReceipt', {
  web: () => import('../../src/receipt-capture-web').then((m) => new m.ReceiptCaptureWeb()),
});

const instance: CaptureReceipt = new CaptureReceipt(plugin);

export const login = async (username: string, password: string, source: string) => {
  const account: Account = {
    username, password, type: accountTypes.from(source)!!
  }
  await instance.login(account).catch((error) => console.log(error));
}

export const accounts = async () => instance.accounts(onAccount => console.log(onAccount));
export const scan = async () => instance.scan((onAccount => console.log(onAccount)));
export const logout = async () => instance.logout();
export const initialize = async () => {
  await instance.initialize(
    'sRwAAAAoY29tLm15dGlraS5zZGsuY2FwdHVyZS5yZWNlaXB0LmNhcGFjaXRvcgY6SQlVDCCrMOCc/jLI1A3BmOhqNvtZLzShMcb3/OLQLiqgWjuHuFiqGfg4fnAiPtRcc5uRJ6bCBRkg8EsKabMQkEsMOuVjvEOejVD497WkMgobMbk/X+bdfhPPGdcAHWn5Vnz86SmGdHX5xs6RgYe5jmJCSLiPmB7cjWmxY5ihkCG12Q==',
    'wSNX3mu+YGc/2I1DDd0NmrYHS6zS1BQt2geMUH7DDowER43JGeJRUErOHVwU2tz6xHDXia8BuvXQI3j37I0uYw==',
  );
};

createApp(App).mount('#app');
