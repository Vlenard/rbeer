if [ -d "dist" ]; then
    rm -r /dist/*
fi
tsc
cd frontend/ 
npm run build