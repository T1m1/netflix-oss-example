# User-Service
- Benutzerverwaltung

## Verwaltung
- Starten: ``` mvn spring-boot:run```


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
