PRAGMA foreign_keys = false;
CREATE TABLE "storage" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL,
  "type" integer NOT NULL,
  "base_path" TEXT NOT NULL,
  "access_url" TEXT NOT NULL,
  "config" TEXT,
  "status" integer NOT NULL,
  "create_time" DATE,
  "update_time" DATE
);
PRAGMA foreign_keys = true;