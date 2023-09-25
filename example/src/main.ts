import './assets/main.css';
import { createApp } from 'vue';
import { ReceiptCapture } from '../../src/receipt-capture';
import type { ReceiptCapturePlugin, ScanType } from '@/receipt-capture-plugin';
import { registerPlugin } from '@capacitor/core';

import App from '@/app.vue';
import type { AccountType } from '@/account';
import { error } from 'console';

const plugin: ReceiptCapturePlugin = registerPlugin<ReceiptCapturePlugin>('ReceiptCapture', {
  web: () => import('../../src/receipt-capture-web').then((m) => new m.ReceiptCaptureWeb()),
});

const instance: ReceiptCapture = new ReceiptCapture(plugin);
let auth = false;
let source = '';
//AMAZON
// const username = 'username';
// const password = 'password';
// source = 'AMAZON';

// *********************

// GOOGLE
// const username = 'username';
// const password = 'password';
// source = 'GMAIL';

// auth = true;

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
      const dateFinal = new Date();
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
  await instance.initialize('licenseKey', 'productKey');
};

createApp(App).mount('#app');
