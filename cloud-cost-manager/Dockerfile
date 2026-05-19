# Stage 1: Build the Spring Boot Backend
FROM maven:3.9.9-eclipse-temurin-21 AS build-stage
WORKDIR /app

# Copy files from the specific folder in your repo
# We copy the pom and src from the 'cloud-cost-manager' directory
COPY cloud-cost-manager/pom.xml .
COPY cloud-cost-manager/src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Final Runtime Image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file
COPY --from=build-stage /app/target/*.jar app.jar

# Standard Render port
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]