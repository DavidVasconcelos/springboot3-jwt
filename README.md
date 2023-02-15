# Spring Boot 3.0 Security with JWT Implementation
This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes the following features:

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Gradle

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Gradle 7+


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/DavidVasconcelos/springboot3-jwt.git`
* Navigate to the project directory: `cd springboot3-jwt`
* Run docker for postgres database: `docker-compose -f postgres-docker.yml up -d`
* Build the project: `./gradlew build`
* Run the project: `./gradlew bootRun`

-> The application will be available at http://localhost:8080.

-> Postman collection: `/postman/postman_collection.json`
