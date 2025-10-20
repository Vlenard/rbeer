-- CreateTable
CREATE TABLE "Beer" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "userId" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "description" TEXT,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" DATETIME NOT NULL,
    "averageRate" REAL DEFAULT 0,
    CONSTRAINT "Beer_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "Rating" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "score" REAL NOT NULL,
    "comment" TEXT,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "beerId" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    CONSTRAINT "Rating_beerId_fkey" FOREIGN KEY ("beerId") REFERENCES "Beer" ("id") ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "Rating_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateIndex
CREATE UNIQUE INDEX "Rating_beerId_userId_key" ON "Rating"("beerId", "userId");
