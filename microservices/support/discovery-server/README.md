# Discovery-Server Eureka

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

