/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

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

export const login = async (username:string, password:string, source:string) =>{
      await instance.login(username, password, source).catch(error=>console.log(error))
}

export const accounts = async () =>{
    await instance.accounts()
}
export const scan = async () =>{
    await instance.scan('PHYSICAL')
}
export const logout = async () =>{
    await instance.logout("vdfDF", "dasdasda", "äsdadadads")
}
export const initialize = async () =>{
    await instance.initialize("vdfDF", "dasdasda")
}
    
createApp(App).mount('#app');
