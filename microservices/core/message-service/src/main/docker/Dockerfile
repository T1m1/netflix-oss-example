FROM java:8

ADD message-0.0.1-SNAPSHOT.jar /app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]