import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import path from 'path'
import pug from './plugins/pug';

// https://vite.dev/config/
export default defineConfig({
  base: "./",
  build: {
    outDir: "../views/app",
    assetsDir: "../../public/app",
  },
  plugins: [
    react({
      babel: {
        plugins: [['babel-plugin-react-compiler']],
      },
    }),
    tailwindcss(),
    pug.transformIndexHtml(),
    pug.renameIndexHtmlToPug()
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src")
    }
  }
})
