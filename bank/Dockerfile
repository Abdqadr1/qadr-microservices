FROM openjdk:17.0-jdk as build

MAINTAINER abdqadr

EXPOSE 8082

COPY target/bank.jar bank.jar

ENTRYPOINT ["java", "-jar", "bank.jar"]