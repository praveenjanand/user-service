FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/user-service-1.jar /app/user-service-1.jar
EXPOSE 8080
CMD ["java", "-jar", "user-service-1.jar"]
