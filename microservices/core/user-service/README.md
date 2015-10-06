# User-Service
- Benutzerverwaltung

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

2. Rest-Controller erstellen mit Gemockter Funktionalität
	```
	@RestController
	public class UserService {
		@RequestMapping("/users/{userId}")
	    public User getProduct(@PathVariable String productId) {		
	        return new User(productId, "Max", "Mustermann");
	    }
	
	}
	```
