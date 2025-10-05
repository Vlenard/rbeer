#rm -r dist
#mkdir dist
#tsc
#cp -r node_modules/ dist/node_modules
rm -r public/app
cd frontend/app
npm run build