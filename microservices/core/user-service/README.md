# User-Service
- Benutzerverwaltung

## Verwaltung
- Starten: ``` mvn spring-boot:run``` oder ```start.bat```

## Dokumentation
- Konfigurationsdateien
	- *bootstrap.(yml,properties)*
		- Wird vor der application.(yml,properties) Konfigurationsdatei aufgerufen.
		- Beim Service-Startup 
		- Enthält den Service Namen
			- Dieser wird verwendet um sich beim Eureka-Server zu registrieren 
	- *application.(yml,properties)*
		- Einsatz von verschiedener Spring-Profiles (Lokal, Cloud, Docker, ...)
		- Host+Port des Eureka-Servers
	- *manifest.(yml,properties)* -> nicht eingesetzt
		- Cloud Einstellungen	
- Eureka Client
	- Wenn sich ein Klient am Eureka Server registriert übermittelt er Daten wie: host, port, homepage, health indicator URL etc
	- Der Server empfängt "heartbeat"-Nachrichten von jeder instanz
	- Wenn innerhalb eines Zeitfensters keine Nachricht mehr empfangen wird, wird die Instanz von der Registry entfernt
	- Aktiviert werden kann der Klient mit *@EnableEurekaClient* oder wenn nur ein Eureka Server eingesetzt wird auch mit *@EnableDiscoveryClient*
	- 

## Implementierung

1.  Microservice mit Spring Boot erstellen 
	```
	@SpringBootApplication
	public class UserServiceApplication {
	    public static void main(String[] args) {
	        SpringApplication.run(UserServiceApplication.class, args);
	    }
	}
	```
	Minimale pom.xml
	```
	  <properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
			<java.version>1.8</java.version>
		</properties>
	
		<dependencies>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter</artifactId>
		  </dependency>
		</dependencies>
	```
	3. Objekt Model erstellen - mit Hilfe von ProjectLomboc
		```
		@Data
		public class User {		
			private String userId;
			private String lastname;
			private String firstname;
			
			public User(String firstname, String lastname) {
				this.userId = UUID.randomUUID().toString();
				this.firstname = firstname;
				this.lastname = lastname;
			}
		}
		```
	Dependency:
		```
			<dependency>
				<groupId>org.projectlombok</groupId>
				<artifactId>lombok</artifactId>
				<version>1.16.6</version>
				<scope>provided</scope>
			</dependency>
		```

	4. Rest-Controller erstellen mit Gemockter Funktionalität
		```
		@RestController
		public class UserService {
			@RequestMapping("/users/{userId}")
		    public User getProduct(@PathVariable String userId) {		
		        return new User(userId, "Max", "Mustermann");
		    }
		}
		```
	
	5. Port-Konfiguration -> für dynamische Portzuweisung
	
	application.yml

	```
	server:
	  port: 0
	```
2. Konfiguration als Eureka-Client -> Zur Anmeldung am Discovery Server
	1. Eureka Dependency hinzufügen
	
		```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
			<version>1.0.0.RELEASE</version>
		</dependency>
		```
	1. *bootstrap.(yml,properties)* - Name des Services festlegen
	
		```
		spring:
		  application:
		    name: user-service	
		```
	2. *application.(yml,proerties)* - Eureka Server "position" festelgen !!! Wird nicht benötigt, lokalhost = default
	
		```
			eureka:
			  client:
			    serviceUrl:
			      defaultZone: http://127.0.0.1:8761/eureka/	
		```
	3. Instanz ID vergeben, damit Eureka erkennt, dass mehrere instanzen eines Services laufen bzw. das man das auf dem Dashboard sieht + Interval für die Anmeldung am Server verringern + fallback wenn keine Einstellungen für die Eureka location angegeben sind
	
		```
			eureka:
			  client:
			    serviceUrl:
			      defaultZone: http://localhost:8761/eureka/
			  instance:
			    leaseRenewalIntervalInSeconds: 10
			    metadataMap:
			      instanceId: ${vcap.application.instance_id:${spring.application.name}:${spring.application.instance_id:${random.value}}}
		```	
	3. Annotation **@EnableDiscoveryClient** an die Service-Application anbringen (Klasse mit main). Wenn noch andere Discovery Clients eingesetzt werden, sollte **@EnableEurekaClient** verwendet werden.
		```
			@SpringBootApplication
			@EnableDiscoveryClient
			public class UserServiceApplication {
			
			    public static void main(String[] args) {
			        SpringApplication.run(UserServiceApplication.class, args);
			    }
			}	
		```
		
