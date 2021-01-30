@REM Run this file from the same directory as mongo-database-creation.js script.

@REM Creates logs and db path directories.
SET DB_PATH=%APPDATA%\interview-server\data
SET LOG_PATH=%APPDATA%\interview-server\logs\database

mkdir %DB_PATH%
mkdir %LOG_PATH%

@REM Starts the server.
start mongod --dbpath=%DB_PATH% --logpath=%LOG_PATH%\interview-server-mongo.log

@REM Waits for the DB to fully start.
timeout 5

@REM Connects to it and creates the DB.
mongo mongo-database-creation.js

@REM Connects to the DB again to shut it down.
mongo admin --eval "db.shutdownServer(); quit();"