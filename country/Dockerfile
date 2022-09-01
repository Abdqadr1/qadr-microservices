FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

COPY target/country.jar country.jar

ENTRYPOINT ["java", "-jar", "country.jar"]