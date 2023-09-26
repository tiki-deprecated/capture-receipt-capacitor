import './assets/main.css';
import { createApp } from 'vue';
import { ReceiptCapture } from '../../src/receipt-capture';
import type { ReceiptCapturePlugin, ScanType } from '@/receipt-capture-plugin';
import { registerPlugin } from '@capacitor/core';

import App from '@/app.vue';
import type { AccountType } from '@/account';

const plugin: ReceiptCapturePlugin = registerPlugin<ReceiptCapturePlugin>('ReceiptCapture', {
  web: () => import('../../src/receipt-capture-web').then((m) => new m.ReceiptCaptureWeb()),
});

const instance: ReceiptCapture = new ReceiptCapture(plugin);
let auth = false;
let source = '';

const username = '';
const password = '';
source = 'GMAIL';

const accountType: AccountType = {
  type: source === 'GMAIL' ? 'EMAIL' : 'RETAILER',
  name: source === 'GMAIL' ? 'Gmail' : 'Amazon',
  key: source,
};

export const login = async () => {
  console.log('teste login');
  console.log(source + ': ' + username);
  console.log(
    auth
      ? await instance.login(source).catch((error) => console.log(error))
      : await instance.login(source, username, password).catch((error) => console.log(error)),
  );
};
export const accounts = async () => {
  console.log('teste accounts');
  console.log(await instance.accounts());
};
export const scan = async () => {
  console.log('teste scan');

  const dateInit = new Date();
  const initialTime = dateInit.getMilliseconds();

  try {
    instance.scan(accountType.type).then((scan) => {
      let dateFinal = new Date();
      const finalTime = dateFinal.getMilliseconds();

      console.log(scan);
      console.log((finalTime - initialTime).toString());
    });
  } catch (e) {
    console.log(e);
  }
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
  );
};

createApp(App).mount('#app');
