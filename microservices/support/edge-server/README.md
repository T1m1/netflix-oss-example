# edge-server mit Zuul


## Zuul

- Ist ein JVM basierter Router
- Server-Seitiges Load-Balancing
- wird bei Netflix eingesetzt für:
  - Authentication
  - Stress-Tests 
  - Canary Testing (Auslieferung von Features an eine bestimmte Gruppe von Usern)
  - Dynamisches Routing
  - Service Einbindung
  - Last Ausgleich
  - Security
- Zuuls Regelwerk erlaubt Regeln und Filter geschrieben in einer beliebigen JVM Sprache. Standardmäig wird Java und Groovy unterstützt.
- Aktivierung mit @EnableZuulProxy an einer Spring-Boot Main Klasse
- Laut Konvention, wird ein Service mit der EurekaID "users" Requests vom Proxy mit /users
  - dies kann man in den Konfigurationen ändern
- 



## Implementierung
1. @EnableZuulProxy an die Spring-Boot Main Klasse
2. Maven Dependencies hinzufgen *org.springframework.cloud-spring-cloud-starter-eureka*, *org.springframework.cloud-spring-cloud-starter-zuul*, *org.springframework.boot-spring-boot-starter-web*
3. Namen für Anwendung vergeben in *bootstrap.yml*
  
  ```
  spring:
    application:
      name: edgeserver
  ```


## Mögliche Einstellungen - *application.yml*
- **server.port** Eingangsport - wird keiner angegeben, default -> 8080
- **zuul.ignoreservices** -  Zu ignorierende Services - mit "*" werden alle bis auf die freigegebenen ignoriert bzw. nicht Sichtbar gemacht
- Bsp.: ignoriert alle bis auf den Service "product". Dieser steht unter dem pfad "/test/**" zur Verfügung
  ```
  zuul.
    ignoredServices: "*"
    routes:
      product:
        path: /test/**
  ```

