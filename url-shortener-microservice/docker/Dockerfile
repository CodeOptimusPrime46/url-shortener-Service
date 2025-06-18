FROM eclipse-temurin:17-jdk
LABEL authors="karthikkumar"

# Set working directory inside container
WORKDIR /app

# Copy the packaged JAR from Maven build
COPY ./target/url-shortener-microservice-*-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
