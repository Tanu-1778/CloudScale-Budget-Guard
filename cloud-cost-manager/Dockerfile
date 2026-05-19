# --- STEP 1: Build the React Frontend ---
FROM node:20-alpine AS frontend-builder
WORKDIR /app/frontend
# We assume the 'cloud' folder is in your GitHub root
COPY cloud/package*.json ./
RUN npm install
COPY cloud/ ./
RUN npm run build

# --- STEP 2: Build the Spring Boot Backend ---
FROM maven:3.9.9-eclipse-temurin-21 AS backend-builder
WORKDIR /app/backend
# Copy the backend files from your root
COPY pom.xml .
COPY src ./src
# Copy the built React files into Spring Boot's static folder
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static
# Build the final JAR
RUN mvn clean package -DskipTests

# --- STEP 3: Create the final Run Image ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=backend-builder /app/backend/target/*.jar app.jar

# Render requirements
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]