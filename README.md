# URL Shortener Service

A simple URL shortener service built with Java, Spring Boot, redis, and mongoDB.

## Features

- Shorten long URLs to unique short codes
- Redirect short URLs to original URLs
- Store URL mappings in Redis for fast access
- RESTful API endpoints

# Should Know and Could Improve

- Redis as a caching layer for fast URL lookups, Here I have used Base62 encoding to generate short URLs. Using Redis increment to generate seed.
    - Possible improvements:
        - When cache restarts and without any AOF(Append Only File) enabled. We will lose the incremented value. So use AOF to persist the incremented value.
        - As of now, we use RDB snapshot periodically to persist the data. But it is not recommended for production use. since we will have conflict in the incremented value incase
          of app failures between snapshot-saving intervals.
- Client side caching, for more frequent requests, we can use client side caching to reduce the load on the server.

## Tech Stack

- Java 17+
- Redis 8.0.2+
- Maven 3.9.9+
- MongoDB 8.0.4+

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/CodeOptimusPrime46/url-shortener-service.git cd url-shortener-service
   ```

2. **Configure Redis:**
    - Ensure Redis is running locally on default port `6379` or update `application.yml` accordingly.

3. **Configure MongoDB:**
    - Ensure MongoDB is running locally or update `application.yml` with your MongoDB connection details.
    - Create a database named `urlshortener` or update the `application.yml` with your preferred database name.

4. **Build and run the application:**

  ```bash
  mvn clean install
  ```

5. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```
   or

  ```bash
   java -jar url-shortener-microservice/target/url-shortener-microservice-0.0.1-SNAPSHOT.jar
   ```

6. **API Usage:**

    - ***Shorten a URL***
      ```
      POST /v1/shorten
      {
        "url": "https://example.com"
      }
      ```
      ***Response:***
      ```
      {
        "shortUrl": "2EA"
      }
      ```

    - **Redirect**
      ```
      GET /v1/{shortUrl}
      ```
      Redirects to the original URL.

    - **Retrieve Long Url**
      ```
      GET /v1/get/{shortUrl}
      ```
      Get the original URL.

## Flow
![Flow.png](Flow.png)

## License

This project is licensed under the MIT License.

