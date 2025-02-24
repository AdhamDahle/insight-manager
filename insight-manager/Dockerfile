# Use the official Eclipse Temurin image for Java 17
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the already built jar file from the target directory
COPY target/insight-manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (8088) and debug port (5005)
EXPOSE 8088 5005

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
