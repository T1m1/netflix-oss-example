# Discovery-Server Eureka 

Dashboard [lokal](http://localhost:8761/)

- [Was ist und was kann Eureka?](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)
- [Server-Client-Kommunikation verstehen.](https://github.com/Netflix/eureka/wiki/Understanding-eureka-client-server-communication)

## Was ist zu beachten?

- Die Adresse vom Eureka ist hinter einem DNS Eintrag zu verstecken. So ist bei einer Änderunge der Adresse keine Anpassung in den Konfigurationsdateien der Services notwendig.

## Server Implementierung

1. Spring Boot Template Application erstellen
2. An der Klasse mit der main-Methode, die Annotation **@EnableEurekaServer** anhängen.
	```
	  @EnableEurekaServer
	  @SpringBootApplication
	  public class DiscoveryServerApplication {
	  
	      public static void main(String[] args) {
	          SpringApplication.run(DiscoveryServerApplication.class, args);
	      }
	  }
	```
    	
  Zusätzliche alle Abhängigkeiten einbinden (pom.xml)
	```
	    <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
	```
3. Eureka-Server Konfigurieren
	1. Port setzen + Instanz mitteilen, sich nicht bei sich selbst zu registrieren  	
		```
		server:
		  port: ${PORT:8761}
		  
		 eureka:
		  client:
		    registerWithEureka: false
		    fetchRegistry: false
		    server:
		      waitTimeInMsWhenSyncEmpty: 0
		```

## Client implementierung
	- Name der Klients wird als  ```spring.application.name``` property in der bootstrap-Datei festgelegt
	- ```org.springframework.cloud:spring-cloud-starter-eureka``` Dependency hinzufügen
	- @EnableEurekaClient an die Klasse mit der main-Methode, damit sich der Client am Server anmeldet

### Zwei oder mehrere Instanzen vom Eureka Server aufsetzen (& auf selber Lokalen Maschine laufen lassen)

- Zuerst muss die Konfiguration im Eureka Server angepasst werden:
	- Es müssen verschiedene Spring Profile erstellt werden
	- Wenn die Instanzen lokal laufen gelassen werden, müssen verschiedene Ports festgelegt werden. Ansonsten müssen verschiedene host angegeben werden. Diese sind statisch.
	- Die defaultZone gibt an, wo der Eureka Server sich registrieren soll -> Beim anderen
- Danach kann das Projekt lokal gestartet werden. Die verschiedenen Profile werden mit *mvn spring-boot:run -Dspring.prfiles.active="local1"* gestartet (profile anpassen)

Eine Beispiel KonfigurationsDatei vom Server:
```
---

spring:
  profiles: local1
server:
  port: 8761
eureka:
  instance:
    hostname: local1
  client:
    serviceUrl:
      defaultZone: http://localhost:8762/eureka
---

spring:
  profiles: local2
server:
  port: 8762
eureka:
  instance:
    hostname: local2
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
```

#### Klient-Seitige Konfiguration:
Wird nicht dringend benötigt. Die Server tauschen auch untereinander ihre Informationen aus.

```
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka,http://localhost:8762/eureka
```
