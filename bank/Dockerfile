FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

COPY target/bank.jar bank.jar

ENTRYPOINT ["java", "-jar", "bank.jar"]