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
- ...
- Der Eureka Server hat keine Backend Speicher in dem er die Informationen über alle Services speichert Alle Instanzen senden in regelmäßigen abständen (per default: 30 Sekunden) "Lebenszeichen", sogenannte "heartbeats". Die Liste aller laufenden instanzen kann im Speicher gehalten werden.
- Clients besitzen ebenfalls eine Kopie einer Liste aller Services im Cache. => Deshalb müssen sie nicht für jeden Request die Registry anfragen
	- Mit dieser liste können die Clients Load Balancing durchführen -> Ribbon kann verwendet werden 
- Die Kombination von 2 Caches macht den Server ziemlich Ausfall sicher, solange es eine instanz gibt, die überwacht, dass der Server wieder gestartet wird
- Noch stabiler/verfübarer wird Eureka, wenn mehrere Instanzen gestartet werden und diese sich gegenseitig registrieren.
	- dazu muss die *serviceUrl* zum anderen Server zeigen
- [Server-Client-Kommunikation verstehen](https://github.com/Netflix/eureka/wiki/Understanding-eureka-client-server-communication)
- Clients:
	- Name der Klients wird als  ```spring.application.name``` property in der bootstrap Datei festgelegt
	- ```org.springframework.cloud:spring-cloud-starter-eureka``` Dependency hinzufügen
	- @EnableEurekaClient an die Klasse mit der main-Methode -> Verifizieren


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
