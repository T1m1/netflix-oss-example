FROM java:8

ADD discovery-server-0.0.1-SNAPSHOT.jar /app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8761

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]