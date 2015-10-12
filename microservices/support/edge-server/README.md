# edge-server mit Zuul


## Zuul

- Ist ein JVM basierter Router
- Server-Seitiges Load-Balancing
- wird bei Netflix eingesetzt für:
  - Authentication
  - Stress-Tests 
  - Canary Testing (Auslieferung von Features an eine bestimmte Gruppe von Usern)
  - Dynamisches Routing
  - Service Einbindung
  - Last Ausgleich
  - Security
- Zuuls Regelwerk erlaubt Regeln und Filter geschrieben in einer beliebigen JVM Sprache. Standardmäig wird Java und Groovy unterstützt.
- Aktivierung mit @EnableZuulProxy an einer Spring-Boot Main Klasse
- Laut Konvention, wird ein Service mit der EurekaID "users" Requests vom Proxy mit /users
  - dies kann man in den Konfigurationen ändern
- 



## Implementierung
1. @EnableZuulProxy an die Spring-Boot Main Klasse
2. 



## Einstellungen
```

```
