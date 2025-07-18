# Use a Java 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR (make sure you run mvn clean package first)
COPY target/*.jar app.jar

# Activate the 'docker' Spring profile
ENV SPRING_PROFILES_ACTIVE=docker

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
