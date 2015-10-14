:: PORTS
SET MONGO_DB_PORT_USER=1001
SET MONGO_DB_PORT_MESSAGE=1002
SET MONGO_DB_PORT_DOCUMENT=1003

:: PATHS
SET MONGO_DB_PATH_USER="C:/data/db-user"
SET MONGO_DB_PATH_MESSAGE="C:/data/db-message"
SET MONGO_DB_PATH_DOCUMENT="C:/data/db-document"

:: mongodb - user
if not exist %MONGO_DB_PATH_USER% mkdir %MONGO_DB_PATH_USER%
start /D "C:/" mongod --dbpath %MONGO_DB_PATH_USER% --port %MONGO_DB_PORT_USER%
:: mongodb - message
if not exist %MONGO_DB_PATH_MESSAGE% mkdir %MONGO_DB_PATH_MESSAGE%
start /D "C:/" mongod --dbpath %MONGO_DB_PATH_MESSAGE% --port %MONGO_DB_PORT_MESSAGE%
:: mongodb - user
if not exist %MONGO_DB_PATH_DOCUMENT% mkdir %MONGO_DB_PATH_DOCUMENT%
start /D "C:/" mongod --dbpath %MONGO_DB_PATH_DOCUMENT% --port %MONGO_DB_PORT_DOCUMENT%

:: support
start /D "microservices/support/discovery-server" mvn spring-boot:run
start /D "microservices/support/edge-server" mvn spring-boot:run

:: core
start /D "microservices/core/user-service" mvn spring-boot:run
start /D "microservices/core/document-service" mvn spring-boot:run
start /D "microservices/core/message-service" mvn spring-boot:run

:: composite
start /D "microservices/composite/mailbox-service" mvn spring-boot:run


:: start web UI
start /D "web" node server.js

