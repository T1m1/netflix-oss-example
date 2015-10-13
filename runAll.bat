:: mongodb
if not exist "C:/data/db-user" mkdir C:/data/db-user
start /D "C:/" mongod --dbpath C:/data/db-user --port 1001

:: support
start /D "microservices/support/discovery-server" mvn spring-boot:run
start /D "microservices/support/edge-server" mvn spring-boot:run

:: core
start /D "microservices/core/user-service" mvn spring-boot:run
start /D "microservices/core/document-service" mvn spring-boot:run
start /D "microservices/core/message-service" mvn spring-boot:run

:: composite
start /D "microservices/composite/mailbox-service" mvn spring-boot:run


