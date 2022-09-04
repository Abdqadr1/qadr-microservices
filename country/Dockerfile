FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

EXPOSE 8081

COPY target/country.jar country.jar

ENTRYPOINT ["java", "-jar", "country.jar"]