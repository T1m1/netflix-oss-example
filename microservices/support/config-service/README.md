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
- Konfigurationsdateien updaten: http://config-server:8888/bus/refresh
- Konfiguration eines einzelnen Services updaten: http://service:port/refresh
  - Restart Funktion aktivieren: "endpoints.restart.enabled: true"

  
