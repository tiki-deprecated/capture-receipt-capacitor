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

export const login = async () =>{
    console.log('teste login')

      console.log(await instance.login("user", "password", "AMAZON").catch(error=>console.log(error)))
}

export const accounts = async () =>{
    console.log('teste accounts')

    await instance.accounts()
}
export const scan = async () =>{
    console.log('teste scan')

    await instance.scan("PHYSICAL")
}
export const logout = async () =>{
    console.log('teste logout')

    await instance.logout()
}
export const initialize = async () =>{
    console.log('teste initialize')
    await instance.initialize("licenseKey", "productKey")
}

createApp(App).mount('#app');