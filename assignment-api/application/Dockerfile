FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar into the container
COPY target/application-0.0.1-SNAPSHOT.jar /app/api-assignment.jar

# Command to run the application
CMD ["java", "-jar", "api-assignment.jar"]
