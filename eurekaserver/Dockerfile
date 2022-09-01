FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

COPY target/eurekaserver.jar eurekaserver.jar

ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]