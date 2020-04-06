FROM openjdk:8-jre-alpine

RUN mkdir app

COPY *.jar /app/demo-0.0.1-SNAPSHOT.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar","/app/demo-0.0.1-SNAPSHOT.jar"]
