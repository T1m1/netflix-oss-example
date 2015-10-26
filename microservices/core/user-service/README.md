# User-Service
- Benutzerverwaltung

## Verwaltung
- MongoDB-Server auf Port 1001 starten und Service mit ``` mvn spring-boot:run``` ausführen
- oder ```start.bat``` aufrufen

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
- **Eureka Client**
	- Wenn sich ein Klient am Eureka Server registriert übermittelt er Daten wie: host, port, homepage, health indicator URL etc
	- Der Server empfängt "heartbeat"-Nachrichten von jeder instanz
	- Wenn innerhalb eines Zeitfensters keine Nachricht mehr empfangen wird, wird die Instanz von der Registry entfernt
	- Aktiviert werden kann der Klient mit *@EnableEurekaClient* oder wenn nur ein Eureka Server eingesetzt wird auch mit *@EnableDiscoveryClient*
	- *@EnableEurekaClient* macht die Applikation zu einer Eureka "Instanz" die sich bei sich selbst registrieren kann und einen "Klient", der die Registry nach der Postion von anderen Services anfragen kann.
	- Der spring.application.name in ist für den Service erforderlich
	- Konfigurations-Optionen sind [hier - EurekaInstanceConfigBean](https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-core/src/main/java/org/springframework/cloud/netflix/eureka/EurekaInstanceConfigBean.java) und [hier - EurekaClientConfigBean](https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-core/src/main/java/org/springframework/cloud/netflix/eureka/EurekaClientConfigBean.java) zu finden 
	- Das registrieren einse Services ist langsam:
		- default Einstellung für eine heartbeat Periode: 30 Sekunden
		- Ein Service ist erst dann verfügbar, bis der Server & der Client die selbe metadata im lokalen cache halten -> das kann bis zu 3 hearbeat-Perioden dauern
		- Die Periode kann angepasst werden -> eureka.instance.leaseRenewalIntervalInSeconds
		- Im Produktivmodus sollte der default Wert verwendet werden -> da interne Berechnungen durchgeführt werden, die Annahmen über die Periode für die leases machen


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
	3. Instanz ID vergeben, damit Eureka erkennt, dass mehrere instanzen eines Services laufen bzw. das man das auf dem Dashboard sieht + Interval für die Anmeldung am Server verringern (+ fallback wenn keine Einstellungen für die Eureka location angegeben sind), (+ eureka.client.healthcheck.enable=true)
	
		```
			eureka:
			  client:
			    serviceUrl:
			      defaultZone: http://localhost:8761/eureka/
			    healthcheck:
      			      enabled: true
			  instance:
			    leaseRenewalIntervalInSeconds: 10
			    metadataMap:
			      instanceId: ${spring.application.name}:${spring.application.instance_id:${random.value}}
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


## MongoDB
1. Hinzufügen der MongoDB Dependency

	```
		<!-- mongodb -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
	```

2. In der **application.yml** host und port der monogodb angeben

	```
	spring:
	  data:
	    mongodb:
	      host: localhost
	      port: 1001
	```

3. **@Id** Annotation an die ID der Bean setzen

	```
		@Id private String userId;
	```
	
4. Repository Klasse erstellen - optional mit von MongoDB vordefinierten Suchfunktionen. Hier wird ein automatisches Mapping an User erstellt. CRUD Funktionen sind mit der Route /user möglich. Möchte man einen anderen Pfad verwenden, kann man dies mit: *@RepositoryRestResource(collectionResourceRel = "benutzer", path = "benutzer")* einstellen
	```
	public interface CustomRepository extends MongoRepository<User, String> {
	
		public User findByFirstName(String firstName);
	
	}
	```
5. Jetzt kann zusätzlich zu den CRUD & Suchfunktionen auch eigene Methoden in der Applikationsklasse erstellt werden. Dazu muss das Repository injected werden.

```
	@Autowired
	private CustomRepository repository;
	
	@RequestMapping(value = "/test/{testname}", method = RequestMethod.POST)
	public void testDB(@PathVariable String testname) {
		repository.save(new User(testname, testname));
		for (User user : repository.findAll()) {
			System.out.println(user.getFirstName() + " " + user.getLastName());
		}
		
		System.out.println("Find user with name: " + testname);
		System.out.println(repository.findByFirstName(testname));
		
	}
```
[quelle](https://spring.io/guides/gs/accessing-data-mongodb/)

## Docker einbinden

- Dependency zum Docker-Maven Plugin von Spotify hinzufügen
- Dazu wird noch die Property *docker.image.prefix* im Maven POM benötigt
- Bsp:
	```
	<properties>
		<docker.image.prefix>t1m1</docker.image.prefix>
	</properties>
	
	<build>
		<plugins>
			<plugin>
				<groupId>com.spotify</groupId>
				<artifactId>docker-maven-plugin</artifactId>
				<version>0.2.3</version>
				<configuration>
					<imageName>${docker.image.prefix}/${project.artifactId}</imageName>
					<dockerDirectory>src/main/docker</dockerDirectory>
					<resources>
						<resource>
							<directory>${project.build.directory}</directory>
							<include>${project.build.finalName}.jar</include>
						</resource>
					</resources>
				</configuration>
			</plugin>
		</plugins>
	</build>
	```

## Config Service verwenden

- Maven Dependency einbinden  

	```
	  <!-- cloud config -->
	               <dependency>
	                       <groupId>org.springframework.cloud</groupId>
	                       <artifactId>spring-cloud-starter-config</artifactId>
	                       <version>1.0.1.RELEASE</version>
	               </dependency>
	               <!-- cloud config bus -->
	               <dependency>
	                       <groupId>org.springframework.cloud</groupId>
	                       <artifactId>spring-cloud-starter-bus-amqp</artifactId>
	                       <version>1.0.0.RELEASE</version>
	               </dependency>
	```

- AutoConfig aktivieren
	- @EnableAutoConfiguration an Applications Klasse
- Wichtig!!! Bevor die Konfiguration vom Config Service genommen werden kann, muss im Config Service ein Post auf /bus/refresh durchgeführt werden
