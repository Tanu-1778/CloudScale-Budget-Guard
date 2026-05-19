# Stage 1: Build the Spring Boot Backend
FROM maven:3.9.9-eclipse-temurin-21 AS build-stage
WORKDIR /app

# Since Root Directory is 'cloud-cost-manager', 
# these files are now in the current folder (.)
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Final Runtime Image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build-stage /app/target/*.jar app.jar

# Render requirements
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]