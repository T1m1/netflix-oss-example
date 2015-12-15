# Hystrix - Dashboard

# Was gibts zu beachten?

- Wir das Tool [Turbine](https://github.com/Netflix/Turbine) von Netflix NICHT eingesetzt, kann nur ein einzige Circuit Breaker angezeigt und analysiert werden.



## Implementierung - ohne Turbine (Zusätzlich ist Turbine zu installieren)

1. Dependencies hinzufügen **org.springframework.cloud:spring-cloud-starter-hystrix-dashboard** 
2. Um das Dashboard verwenden zu können muss die Annotation **@EnableHystrixDashboard** an die Applikationsklasse angehängt werden
3. In der **application.yml** kann ein fixer Port angegeben werden *server:\n  port: 8383*
3. In dem zu beobachtetem Service muss die dependency **org.springframework.boot:spring-boot-starter-actuator** hinzugefügt werden um den Stream zu aktivieren


Jetzt kann auf http://host:8383/hystrix die url zum gewünschten Stream angegeben werden. Die Stream-URL wird zusammengebaut aus: http://host:port-zum-service/hystrix.stream 
Bis der Stream aktiv ist kann es einige Sekunden dauern


