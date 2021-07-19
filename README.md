# Distance Calculator

This service is designed to return people who are listed as either living in London, or whose current coordinates are within 50 miles of London from an external API.

By default, the application will run on port 8080 and exposes a single endpoint to meet the above requirement.

```
http://localhost:8080/get-users-by-radius-to-london
```

## Running the application
This project uses Java 16 and is managed by Maven. To start the application first build the project using:

```
mvn clean package
```
The project can then be started using the docker-compose file included in this repository
```
docker-compose up
```
Alternatively the project can be started using the Java command
```
java -jar target/distance-calculator-0.0.1-SNAPSHOT.jar
```
## Configuration
The following environment variables can be configured.

| Value | Description | Default |
| ------ | ------ | ------ |
| SERVER_PORT | Port the application will run on | 8080 |
| USER_API_URL | API of external service for getting user data | https://bpdts-test-app.herokuapp.com |


