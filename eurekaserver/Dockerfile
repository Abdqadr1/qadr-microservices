FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

EXPOSE 8761

COPY target/eurekaserver.jar eurekaserver.jar

ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]