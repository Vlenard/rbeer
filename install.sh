echo "Starting installation process..."
echo "Installing backend dependencies..."
npm install
npx prisma generate
chmod +x ./dev.sh
chmod +x ./build.sh
echo "Installing frontend dependencies..."
cd frontend/app
npm install
