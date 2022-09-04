FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

EXPOSE 8000

COPY target/gateway.jar gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]