/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

/**
 ReceiptCapturePlugin

 A Capacitor plugin for handling receipt capture functionality.
 */
CAP_PLUGIN(ReceiptCapturePlugin, "ReceiptCapture",
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(login, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(logout, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(accounts, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(scan, CAPPluginReturnPromise);
)
