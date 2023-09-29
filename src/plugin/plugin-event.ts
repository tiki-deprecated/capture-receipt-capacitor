/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export enum PluginEvent {
  onAccount = 'onAccount',
  onComplete = 'onComplete',
  onReceipt = 'onReceipt',
  onError = 'onError',
}

export const toPluginEvent = (value: string): PluginEvent | undefined => {
  const key = Object.keys(PluginEvent).find((key) => PluginEvent[key as keyof typeof PluginEvent] === value);
  return key !== undefined ? PluginEvent[key as keyof typeof PluginEvent] : undefined;
};
