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



