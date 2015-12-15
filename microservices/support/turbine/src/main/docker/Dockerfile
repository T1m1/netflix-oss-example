FROM java:8

ADD turbine-0.0.1-SNAPSHOT.jar /app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8989

ENV JAVA_OPTS -Dspring.profiles.active=docker
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]