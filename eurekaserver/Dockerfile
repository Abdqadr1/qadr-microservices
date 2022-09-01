FROM openjdk:17.0-jdk as build

MAINTAINER abd_qadr

COPY target/eurekaserver-1.0.jar eurekaserver-1.0.jar

ENTRYPOINT ["java", "-jar", "eurekaserver-1.0.jar"]