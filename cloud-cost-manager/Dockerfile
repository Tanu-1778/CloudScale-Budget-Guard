# Stage 1: Build the React Frontend
FROM node:20-alpine AS web-build
WORKDIR /app/frontend
COPY cloud/package*.json ./
RUN npm install
COPY cloud/ ./
RUN npm run build

# Stage 2: Build the Spring Boot Backend
FROM maven:3.9.9-eclipse-temurin-21 AS api-build
WORKDIR /app/backend
COPY cloud/backend/pom.xml .
COPY cloud/backend/src ./src

# Copy the frontend build to the backend's static resources
# Note: Using 'web-build' instead of 'frontend-build' to avoid naming conflicts
COPY --from=web-build /app/frontend/backend/src/main/resources/static ./src/main/resources/static

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 3: Final Runtime Image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=api-build /app/backend/target/*.jar app.jar

# Render uses the PORT environment variable
ENV PORT=8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]