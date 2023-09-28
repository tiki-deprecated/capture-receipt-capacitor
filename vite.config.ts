/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

import { defineConfig } from "vite";
import * as path from "path";

module.exports = defineConfig({
  build: {
    lib: {
      entry: path.resolve(__dirname, "src/index.ts"),
      name: "capture-receipt",
      fileName: (format) => `capture-receipt.${format}.js`,
    },
    rollupOptions: {
      input: 'dist/esm/index.js',
      external: ['@capacitor/core'],
      output: [
        {
          file: 'dist/plugin.js',
          format: 'iife',
          name: 'capacitorReceiptCapture',
          globals: {
            '@capacitor/core': 'capacitorExports',
          },
          sourcemap: true,
          inlineDynamicImports: true,
        },
        {
          file: 'dist/plugin.cjs.js',
          format: 'cjs',
          sourcemap: true,
          inlineDynamicImports: true,
        },
      ]
    },
    emptyOutDir: false,
  },
});
