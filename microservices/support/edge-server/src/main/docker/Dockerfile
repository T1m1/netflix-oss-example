FROM java:8

ADD edge-server-0.0.1-SNAPSHOT.jar /app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 1111

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]