import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import path from 'path'
import handlebars from './plugins/handlebars'

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
    handlebars.renameIndexHtmlToPug(),
    handlebars.transformIndexHtml()
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src")
    }
  }
})
