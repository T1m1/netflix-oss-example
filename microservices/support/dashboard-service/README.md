# Hystrix - Dashboard

Um den Status der Circuit-Breaker in den Services (Mailbox-Service) zu erhalten, kann das Dashboard von Hystrix eingesetzt werden. 

- Das Dashboard bietet Enwicklern oder anderen die Möglichkeit Statistiken, wie z.B. über erfolgreiche und fehlgeschlagene Anfragen, über die Circuits zu erhalten.
- Des weiteren bietet Netflix ein weiteres Tool namens Turbine an. Turbine hilft mehrere Streams von Hystrix zu einem zu aggregieren. So muss nicht die ganze Zeit zwischen den instanzen der Services gewechselt werden.



## Implementierung - ohne Turbine

1. Dependencies hinzufügen **org.springframework.cloud:spring-cloud-starter-hystrix-dashboard** 
2. Um das Dashboard verwenden zu können muss die Annotation **@EnableHystrixDashboard** an die Applikationsklasse angehängt werden
3. In der **application.yml** kann ein fixer Port angegeben werden *server:\n  port: 8383*
3. In dem zu beobachtetem Service muss die dependency **org.springframework.boot:spring-boot-starter-actuator** hinzugefügt werden um den Stream zu aktivieren


Jetzt kann auf http://host:8383/hystrix die url zum gewünschten Stream angegeben werden. Die Stream-URL wird zusammengebaut aus: http://host:port-zum-service/hystrix.stream 
Bis der Stream aktiv ist kann es einige Sekunden dauern
