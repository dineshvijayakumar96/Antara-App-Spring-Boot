FROM openjdk:17-jdk-alpine

WORKDIR /antaracares

COPY target/antaracares-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]