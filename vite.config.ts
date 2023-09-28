/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { defineConfig } from 'vite';
import * as path from 'path';

export default defineConfig({
  build: {
    lib: {
      entry: path.resolve(__dirname, 'src/index.ts'),
      name: 'capture-receipt',
      fileName: (format) => `capture-receipt.${format}.js`,
    },
    rollupOptions: {
      external: ['@capacitor/core', '@capacitor/ios', '@capacitor/android'],
      output: {
        assetFileNames: (assetInfo): string => assetInfo.name!,
        exports: 'named',
      },
    },
    emptyOutDir: false,
  },
});
