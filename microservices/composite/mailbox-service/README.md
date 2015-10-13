# Mailbox Service
Liefert alle Nachrichten eines bestimmten Benutzers inkluse der Informationen über die betreffenden Personen und die Anhänge

- Holt sich alle Nachrichten eines bestimmten Users
- Bezieht Informationen über die User und die Angehängten Dokumente über den Dokumenten- und Benutzerservice
- Baut ein Response Objekt zusammen

## Dokumentation
- @Autowire - "Verdrahten" von Klassen 
- @Component - Makiert eine Java Klasse als Bean, sodass der Komponenten-Scan-Mechanismus von Spring die Klasse in der Anwendung finden und verwenden kann.

## Imlementierung

### Andere Services verwenden
1. Um eine Klasse Sichtbar für den Spring-Scan Mechanismus zu machen, muss sie zuerst mit der Annotation @Component versehen werden
2. Mit Hilfe von @Autowired muss der DiscoveryClient eingebunden werden, um die ServiceUrl eines Services mit einem bestimmten Namen zu erhalten
3. Den Namen/ID des benötigten Services kann man der Methode *getNextServerFromEureka* wie folgt mitgeben: *InstanceInfo instance = discoveryClient.getNextServerFromEureka("message-service", false);*
4. Aus dem *instance* Objekt kann man jetzt Informationen wie z.B. den Host, den Port oder die HomepPageUrl


## Ribbon - Client Side Load Balancer
- Ein Klient-Seitiger Load-Balancer der einem viel Kontrolle über des verhalten von HTTP und TCP Klients gibt.
- Netflix's Feign verwendet Ribbon out of the box
- Ein zentrales Konzept ist das 'Naming' der Klients/Services
- Jeder Load Balancer ist Teil eines ensembles von Komponenten, die zusammenarbeiten um einen Remote Server anzufragen
- Spring Cloud erstellt für jeden "Named" Klient ein neues Ensemble als einen *ApplicationContext*. Dazu wird die *RibbonClientConfiguration* verwendet
  - Diese beinhaltet einen *ILoadBalancer*, einen *RestClient* und einen *ServerListFilter*
- Es besteht eine große Auswahl an Konfigurationsmöglichkeiten
- Vorgehensweise:
  - Der Klient holt sich eine Liste aller verfügbaren Service-Instanzen und kann daraufhin mit Hilfe von Ribbon Load-Balancing durchführen

### Ribbon API verwenden 
1. Dependency **org.springframework.cloud:spring-cloud-starter-ribbon** hinzufügen
2. Autowire *LoadBalancerClient* & Service mit Hilfe der *choose* Methode wählen
```
public class MyClass {
    @Autowired
    private LoadBalancerClient loadBalancer;

    public void doStuff() {
        ServiceInstance instance = loadBalancer.choose("stores");
        URI storesUri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
        // ... do something with the URI
    }
}
```
[quelle](http://projects.spring.io/spring-cloud/spring-cloud.html#spring-cloud-ribbon)

## Hystrix - Circuit Breaker
Wenn ein Service keine Response gibt (Timeout oder Kommunikationserror). Kann Hystrix den Aufruf zu einer internen Fallback Methode weiterleiten. Wenn der Service mehrfach aufgerufen wird, öffnet Hystrix den Circuit Breakter und ruft sofort die Fallback Methode auf (Fast Fail). Um zu überprüfen, ob der Service wieder zur Verfügung steht lässt Hystrix immer wieder einzelne Requests durch.
- Hystrix bietet implmenetierungen von oft eingesetzten "fault-Tolerence" Pattern für Verteilte Systeme wie z.b. Circuit-Breakers und Bulkheads
- Ist eine Bibliothek die zur Kontrolle der Kommunikation zwischen verteilten Services dient.
- Hystrix wurde entworfen für:
  - Bietet Schutz und Kontrolle über Latenzzeit und Ausfall bei Aufruf von anderen Services 
  - Stopt das Fehler tiefer ins System eindringen
  - Schnelles Fehlschlagen und rasche wiederherstellen
  - Fallback und kontrolliertes Verhalten
  - Aktiveren von quasi Echtzeit -Monitoring, -Warningen und Einsatzkontrolle
- Allgemein
  - Verteilte Anwendungen haben oft viele Abhängigkeiten. Jeder dieser Abhängigkeiten kann zu jeder Zeit ausfallen. Die Host-Anwendung sollte vor diesen Risiken geschützt sein. 
- Hystrix verwendet Isolations-Techniken wie z.B. (um die Auswirkungen von Abhängigkeiten zu verhindern/minimieren)
  - Buklhead:
  - Swimlane
  - Circuit-Breaker

  
### Hystrix einbinden 
1. Dependency hinzufügen

  ```
 <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
			<version>1.0.0.RELEASE</version>
		</dependency>
  ```
  
2. Hystrix Circuit Breaker aktivieren mit **@EnableCircuitBreaker** an die Spring Boot Anwendung
3. Methode die Hystrix verwalten soll anotieren mit **@HystrixCommand** und dort die Fallback Methode angeben. Diese implementieren..

  ```
   @HystrixCommand(fallbackMethod = "defaultUser")
    public ResponseEntity<List<Message>> getUser(int userId) {
        ...
    }
    
    public ResponseEntity<List<Message>> defaultUser(int userId) {
        ...
    }
  ```
  
[How-To-Use](https://github.com/Netflix/Hystrix/wiki/How-To-Use)
