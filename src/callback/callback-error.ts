/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export class CallbackError {
  message: string;
  code?: number;

  constructor(message: string, code?: number) {
    this.message = message;
    this.code = code;
  }

  static from = (error: CallbackError): CallbackError => new CallbackError(error.message, error.code);

  toString = (): string => (this.code != undefined ? `${this.code}:${this.message}` : this.message);
}
