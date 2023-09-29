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
      name: 'index',
      formats: ['es', 'cjs'],
      fileName: (format) => `index.${format}.js`,
    },
    rollupOptions: {
      external: ['@capacitor/core'],
      output: {
        assetFileNames: (assetInfo): string => assetInfo.name!,
        exports: 'named',
        globals: {
          '@capacitor/core': 'capacitorExports',
        },
        sourcemap: true,
        inlineDynamicImports: true,
      },
    },
    emptyOutDir: false,
  },
});
