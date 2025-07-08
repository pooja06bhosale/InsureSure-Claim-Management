# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the volume to /tmp to prevent warnings from Spring Boot when running in Docker
VOLUME /tmp

# Copy the Spring Boot JAR file from the target folder to the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Specify the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
