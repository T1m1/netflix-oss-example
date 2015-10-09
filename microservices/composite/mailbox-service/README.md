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
