import path from 'path'
import fs from 'fs';

const templatePug = `doctype html
html(lang="en")
  head
    meta(charset="UTF-8")
    link(rel="icon", type="image/svg+xml", href="./vite.svg")
    meta(name="viewport", content="width=device-width, initial-scale=1.0")
    title frontend
    script(type="module", crossorigin="", src="entry-main.js")
    link(rel="stylesheet", crossorigin="", href="entry-main.css")
  body
    #root`;

const transformIndexHtml = () => {
  return {
    name: 'html to pug',
    transformIndexHtml(html: string, { server }: { server: boolean }) {
      if (server) return html; // skip during dev

      const jsRegex = /<script[^>]+src=["']([^"']+)["']/gi;
      const cssRegex = /<link[^>]+rel=["']stylesheet["'][^>]*href=["']([^"']+)["']/gi;

      // Extract JS URLs
      const jsUrls = [];
      let jsMatch;
      while ((jsMatch = jsRegex.exec(html)) !== null) {
        jsUrls.push(jsMatch[1]);
      }

      // Extract CSS URLs
      const cssUrls = [];
      let cssMatch;
      while ((cssMatch = cssRegex.exec(html)) !== null) {
        cssUrls.push(cssMatch[1]);
      }
      

      const jsFileName = jsUrls[0] ? path.basename(jsUrls[0]) : 'entry-main.js';
      const cssFileName = cssUrls[0] ? path.basename(cssUrls[0]) : 'entry-main.css';
      
      return templatePug.replace('entry-main.js', `./${jsFileName}` || 'entry-main.js')
                        .replace('entry-main.css', `./${cssFileName}` || 'entry-main.css');
    }
  }
};

const renameIndexHtmlToPug = () => {
  return {
    name: "rename index.html to index.pug",
    closeBundle() {
      const buildDir = path.resolve(__dirname, "../../views/app");
      if (fs.existsSync(buildDir)) {
        fs.readdirSync(buildDir).forEach((file: any) => {
          if (file === "index.html") {
            fs.renameSync(
              path.join(buildDir, file),
              path.join(buildDir, "index.pug")
            );
          }
        });
      }
    }
  }
}

export default {
  transformIndexHtml,
  renameIndexHtmlToPug
};