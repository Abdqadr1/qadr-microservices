FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

COPY target/gateway.jar gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]