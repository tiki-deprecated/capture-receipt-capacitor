/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

export interface ReceiptCapturePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
