# Bachelor-Projekt



## System & Setup
### Entwicklungs-System
- Windows 7 - 64 Bit
- Spring-Tool-Suite 3.6.4
- MongoDB 3.0

#### WebUI
- Node v4.2.1
- Bower1.5.3

### Service-Landschaft
- 3 Core Services
- 3x MongoDB Instanzen auf Port 1001 (User), 1002 (Message), 1003 (Document)
- 1 Composite Service (Hystrix - Circuit Breaker, Ribbon - LoadBalancer)
- 1 Eureka Server
- 1 Edge Server (Zuul - Router, Ribbon - LoadBalancer)

### Starten
- Service-Landschaft mit Hilfe von ```startAll.bat``` starten
- besser: docker-compose up


## Implementierungs-Schritte
1. [Core-Services](https://github.com/T1m1/bachelor-project/blob/master/microservices/core/user-service/README.md) erstellen
2. [Discovery-Server](https://github.com/T1m1/bachelor-project/blob/master/microservices/support/discovery-server/README.md) (Eureka-Server) erstellen
3. Core-Services am Discovery-Service anmelden lassen
4. Routing mit [Zuul](https://github.com/T1m1/bachelor-project/tree/master/microservices/support/edge-server/README.md)
5. Load-Balancing mit [Ribbon](https://github.com/T1m1/bachelor-project/tree/master/microservices/composite/mailbox-service#ribbon---client-side-load-balancer)
6. Circuit Breaker mit [Hystrix](https://github.com/T1m1/bachelor-project/tree/master/microservices/composite/mailbox-service#hystrix---circuit-breaker)
7. Hystrix [Dashboard] (https://github.com/T1m1/bachelor-project/tree/master/microservices/support/dashboard-service)
8. [WebUI] (https://github.com/T1m1/bachelor-project/tree/master/web)




