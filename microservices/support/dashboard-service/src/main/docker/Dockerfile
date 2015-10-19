FROM java:8

ADD dashboard-0.0.1-SNAPSHOT.jar /app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8383

ENTRYPOINT ["java","-jar","/app.jar"]