:: support
start /D "microservices/support/discovery-service" mvn spring-boot:run


:: core
start /D "microservices/core/user-service" mvn spring-boot:run
start /D "microservices/core/document-service" mvn spring-boot:run
start /D "microservices/core/message-service" mvn spring-boot:run

