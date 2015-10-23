FROM java:8
ADD config-service-0.0.1-SNAPSHOT.jar app.jar

VOLUME /tmp
VOLUME /target

RUN bash -c 'touch /app.jar'

EXPOSE 8888

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]