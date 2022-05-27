FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} myapp.jar
COPY src/main/resources/data.json /data.json
ENTRYPOINT ["java","-jar","/myapp.jar", "/data.json", "/logs/logs.log"]