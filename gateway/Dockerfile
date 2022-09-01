FROM openjdk:17.0-jdk as build

MAINTAINER abd_qadr

COPY target/gateway-1.0.jar gateway-1.0.jar

ENTRYPOINT ["java", "-jar", "gateway-1.0.jar"]