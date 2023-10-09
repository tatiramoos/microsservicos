FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
EXPOSE 8761

ARG EUREKA_SERVER=localhost
ARG KEYCLOAK_SERVER=localhost
ARG KEYCLOAK_PORT=8081

ENTRYPOINT java -jar -Dspring.profiles.active=production app.jar