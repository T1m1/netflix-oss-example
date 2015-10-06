# User-Service
- Benutzerverwaltung

## Implementierung

-  Microservice mit Spring Boot erstellen 
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
