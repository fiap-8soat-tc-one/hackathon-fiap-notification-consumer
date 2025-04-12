FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS deploy

LABEL org.opencontainers.image.title="Hackaton Fiap Notification Consumer"
LABEL org.opencontainers.image.description="Hackaton Fiap Notification Consumer 8SOAT"
LABEL org.opencontainers.image.version="3.0.0"
LABEL org.opencontainers.image.url="https://github.com/fiap-8soat-tc-one/hackathon-fiap-notification-consumer"
LABEL org.opencontainers.image.source="https://github.com/fiap-8soat-tc-one/hackathon-fiap-notification-consumer"
LABEL org.opencontainers.image.authors="FIAP 8SOAT TEAM 32"
LABEL org.opencontainers.image.licenses="GNU General Public License v3.0"

COPY --from=build /app/target/*.jar /app/hackathon-notification-consumer.jar

ENTRYPOINT ["java", "-jar", "hackathon-notification-consumer.jar"]
