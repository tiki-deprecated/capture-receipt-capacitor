/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export enum CallbackEvent {
  onAccount = 'onAccount',
  onComplete = 'onComplete',
  onReceipt = 'onReceipt',
  onError = 'onError',
}

export const toCallbackEvent = (value: string): CallbackEvent | undefined => {
  const key = Object.keys(CallbackEvent).find((key) => CallbackEvent[key as keyof typeof CallbackEvent] === value);
  return key !== undefined ? CallbackEvent[key as keyof typeof CallbackEvent] : undefined;
};
