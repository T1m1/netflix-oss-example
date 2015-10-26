## Config Service

# How To

- Konfigurationsdateien sind auf Github in folgendem Repo: [TODO]
- Format:

----
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
----

- Port: 8888
- Konfigurationsdateien updaten: POST http://config-server:8888/bus/refresh (Muss beim 1. starten durchgeführt werden)
- Konfiguration eines einzelnen Services updaten: http://service:port/refresh
  - Restart Funktion aktivieren: "endpoints.restart.enabled: true"

  
