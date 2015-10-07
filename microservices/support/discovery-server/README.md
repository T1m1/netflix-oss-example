# Discovery-Server Eureka

TODO: 
- Wie mehrere Instanzen des Eureka Servers aufsetzen / verwalten 

## Dokumentation
- Service Registration Server (Endeckung, Feststellung, ..) -> Eureka Server
- Führt Buch über die im Netzwerk verfügbaren Dienste
- Eine Spring-Anwendung kann als Instanz dienen:
 - als Dienst registrieren
 - als Klient agieren 
     - dann kann die Anwendung andere Services finden
- Der Eureka-Server kann als Spring-Boot Anwendung gestartet werden, das die Nutzung vereinfacht
- Rest basierter Server der Primär in der Amazon Web Service (AWS) Cloud eingesetzt wird
- Eureka kommt mit einer Java-basierenden Klient komponente, wodurch die Kommunikation mit dem Server vereinfacht wird
- Einsatz von Eureka bei Netflix: **TODO**

## Implementierung

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
