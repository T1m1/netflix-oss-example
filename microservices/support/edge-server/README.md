# edge-server mit Zuul


## Zuul

[Was ist und was kann Zuul?](http://techblog.netflix.com/2013/06/announcing-zuul-edge-service-in-cloud.html) 

## Was ist zu beachten?
- Der Aufruf einerr Anfrage ist gekapselt innerhalb eines hystrix Kommandos. Aus diesem Grund ist hier ebenfalls eine Anpassung notwendig, sollte das Timeout für Hystrix verändert werden wollen.
- Dank der Möglichkeit Filter in verschiedenen "Lebenszyklen" einer Anfrage zu implementieren, ist Zuul sehr mächtig (Möglichkeit für Fehlerloggin, Metriken, statische Antworten, ...) 
- Am Besten Zuul auf einem separaten Rechner laufen lassen. Falls ein Service auf dem Rechner viele Ressourcen benötigt, kann es dazu führen das Zuul nicht so viel Zeit zum Bearbeiten von Anfragen erhält und dann Anfragen aufgrund von Timeouts abbricht.

## Offene Fragen
- Stellt Zuul das Bottlnenck nach außen dar? Ist es möglich mehrere Zuul Instanzen laufen zu lassen? Wenn ja, wird dafür ein Extra Hardware Load Balancer benötigt oder kann das auch mit der Netflix OSS gelöst werden?

## Implementierung
1. @EnableZuulProxy an die Spring-Boot Main Klasse
2. Maven Dependencies hinzufgen *org.springframework.cloud-spring-cloud-starter-eureka* für die Registrierung am Eureka, *org.springframework.cloud-spring-cloud-starter-zuul* für die Funktionalität von Zuul und *org.springframework.boot-spring-boot-starter-web*
3. Namen für Anwendung vergeben in *bootstrap.yml* um ihn im Eureka Dashboard zu identifizieren. (Und damit andere ihn finden können)
  
  ```
  spring:
    application:
      name: edgeserver
  ```
4. Routen beliebig konfigurieren z.b. vom Mailbox-Service -> /mailbox/
  
  ```
  zuul.
    routes:
      mailbox-service:
        path: /mailbox/**
  ```
  oder message-service verbergen
  
  ```
  zuul:
    ignoredServices: "message-service"
  ``` 


## Mögliche Einstellungen - *application.yml*
- **server.port** Eingangsport - wird keiner angegeben, default -> 8080
- **zuul.prefix** Prefix für routen konfigurierbar (zuul:\n  prefix: api)
- **zuul.ignoreservices** -  Zu ignorierende Services - mit "*" werden alle bis auf die freigegebenen ignoriert bzw. nicht Sichtbar gemacht
- Bsp.: ignoriert alle bis auf den Service "product". Dieser steht unter dem pfad "/test/\*\*" zur Verfügung

  ```
  zuul.
    ignoredServices: "*"
    routes:
      product:
        path: /test/**
  ```
  
  [Weitere Einstellmöglichkeiten](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html) Darunter z.B. auch Hystrix Timeouts
