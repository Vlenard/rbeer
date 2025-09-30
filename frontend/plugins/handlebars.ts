import path from 'path'
import fs from 'fs';

const transformIndexHtml = () => {
  return {
    name: 'html to pug',
    transformIndexHtml(html: string, { server }: { server: boolean }) {
      if (server) return html; // skip during dev

      // replace <script src="...">
      html = html.replace(
        /(<script[^>]*src=["'])([^"']+)(["'][^>]*><\/script>)/g,
        (_match, before, src, after) => {
          const fileName = path.basename(src);
          return `${before}./${fileName}${after}`;
        }
      );

      // replace <link href="...">
      html = html.replace(
        /(<link[^>]*href=["'])([^"']+)(["'][^>]*>)/g,
        (_match, before, href, after) => {
          const fileName = path.basename(href);
          return `${before}./${fileName}${after}`;
        }
      );

      return html;
    }
  }
};

const renameIndexHtmlToPug = () => {
  return {
    name: "rename index.html to index.handlebars",
    closeBundle() {
      const buildDir = path.resolve(__dirname, "../../views/app");
      if (fs.existsSync(buildDir)) {
        fs.readdirSync(buildDir).forEach((file: any) => {
          if (file === "index.html") {
            fs.renameSync(
              path.join(buildDir, file),
              path.join(buildDir, "index.handlebars")
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