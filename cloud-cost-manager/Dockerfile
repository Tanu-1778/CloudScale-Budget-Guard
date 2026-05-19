# Stage 1: Build the Spring Boot Backend
FROM maven:3.9.9-eclipse-temurin-21 AS build-stage
WORKDIR /app

# Copy the pom.xml and the entire src folder (which includes your static frontend)
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Final Runtime Image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build-stage /app/target/*.jar app.jar

# Render uses the PORT environment variable (default 8080)
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]