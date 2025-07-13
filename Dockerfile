# Use Java 17 runtime
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR into the container
COPY target/autocomplete-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 9090

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
