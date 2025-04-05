FROM openjdk:17-alpine
COPY ./target/*.jar /app/
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "franquicias-app-webflux-0.0.1-SNAPSHOT.jar"]