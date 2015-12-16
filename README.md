# Netflix OSS Beispielanwendung

Beispielprojekt zur Validierung der Netflix Open Source Software. 

### Starten auf Windwos
- Service-Landschaft mit Hilfe von ```startAll.bat``` starten

### Starten mit Docker
- Docker Images bauen: ```./buildDocker.sh```
- Mit Hilfe von Docker-Compose starten: ```docker-compose up```


## Implementierungs-Schritte
1. [Core-Services](https://github.com/T1m1/bachelor-project/blob/master/microservices/core/user-service/README.md) erstellen
2. [Discovery-Server](https://github.com/T1m1/bachelor-project/blob/master/microservices/support/discovery-server/README.md) (Eureka-Server) erstellen
3. Core-Services am Discovery-Service anmelden lassen
4. Routing mit [Zuul](https://github.com/T1m1/bachelor-project/tree/master/microservices/support/edge-server/README.md)
5. Load-Balancing mit [Ribbon](https://github.com/T1m1/bachelor-project/tree/master/microservices/composite/mailbox-service#ribbon---client-side-load-balancer)
6. Circuit Breaker mit [Hystrix](https://github.com/T1m1/bachelor-project/tree/master/microservices/composite/mailbox-service#hystrix---circuit-breaker)
7. Hystrix [Dashboard] (https://github.com/T1m1/bachelor-project/tree/master/microservices/support/dashboard-service) + [Turbine](https://github.com/T1m1/netflix-oss-example/tree/master/microservices/support/turbine) 
8. [WebUI] (https://github.com/T1m1/bachelor-project/tree/master/web)
9. [ConfigServer](https://github.com/T1m1/netflix-oss-example/tree/master/microservices/support/config-service)


### System & Setup
#### Entwicklungs-System
- Windows 7 - 64 Bit
- Spring-Tool-Suite 3.6.4
- MongoDB 3.0

##### WebUI
- Node v4.2.1
- Bower1.5.3

#### Service-Landschaft
- 3 Core Services
- 3x MongoDB Instanzen auf Port 1001 (User), 1002 (Message), 1003 (Document)
- 1 Composite Service (Hystrix - Circuit Breaker, Ribbon - LoadBalancer)
- 1 Eureka Server
- 1 Edge Server (Zuul - Router, Ribbon - LoadBalancer)


### Nützliches

#### Docker Kommandos

- Alle Container stoppen: ```docker stop $(docker ps -a -q)```
- Alle Images löschen: ```docker rmi -f $(docker images -q)```

#### Docker-Compose Kommandos

- Systemlandschaft starten: ```docker-compose up```
- Container Skalieren: ```docker-compose scale name=NUMBEROFINSTANCES```
