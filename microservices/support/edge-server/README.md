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


## Einstellungen - *application.yml*
  ```
  # Eingangsport - wird keiner angegeben, default -> 8080
  server:
    port: 8765
  
  # alle services ignorieren, bis auf freigegebene
  # wenn diese Konfiguration nicht mit angegeben wird, können alle services direkt über zuul aufgerufen werden z.b. /user-service/user
  zuul.
    ignoredServices: "*"
    routes:
      product:
        path: /test/**
  ```

