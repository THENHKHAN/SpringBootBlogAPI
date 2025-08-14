# Spring Boot Learning
Welcome to the **Spring Boot Learning With SpringBootBlogAPI** repository! In this project, we explore various aspects of the `Spring Boot` Java framework through a series of mini-projects and exercises. Each branch(used earlier before moving into this repo) represents a specific feature or concept implemented in Spring Boot, ranging from basic CRUD operations to advanced security and validation mechanisms. <br>
Here you can see swagger UI- Api exposed
[swaggerUI1](https://github.com/THENHKHAN/SpringBootBlogAPI/tree/main/springboot-blog-restapi/Imp_SS_EndPointsCLient/17.1_SwaggerUI_exposes_.png) 
[swaggerUI1](https://github.com/THENHKHAN/SpringBootBlogAPI/blob/main/springboot-blog-restapi/Imp_SS_EndPointsCLient/17.2_SwaggerUI_exposes_.png)

---

Architecture and Design

This project implements a Blog API backend using Spring Boot, following a layered architecture pattern to separate concerns and ensure scalability and maintainability.

High-Level Architecture

Controller Layer: Handles incoming HTTP requests and returns responses. Validates inputs and forwards requests to the service layer.

Service Layer: Contains the core business logic for managing blog posts, including create, read, update, and delete operations.

Repository Layer: Interfaces with the database using Spring Data JPA to perform CRUD operations on entities.

Security Layer: Implements user authentication and authorization to protect endpoints.

Exception Handling: Global handlers manage errors and provide consistent error responses.

Low-Level Design

Key classes and their responsibilities:

BlogPost: Represents blog posts with fields such as title, content, author, and timestamps.

User: Represents users with authentication-related data.

BlogPostService: Provides methods to manage blog posts and enforce business rules.

BlogPostRepository: Interfaces with the database for data persistence.

ExceptionHandler: Handles custom exceptions such as “PostNotFound”.

Learning and Ownership

This project was initially built following a Udemy course to learn Spring Boot fundamentals. Since then, I have extended it by adding:

Custom exception handling

Input validation

Basic authentication

Thoughtful API structure following best practices

I am actively working on adding features such as pagination, logging, and automated tests to improve its robustness.

---

## Getting Started

To get started with any of the branches, follow these steps:

1. **Clone the repository:**

    ```sh
    git clone https://github.com/THENHKHAN//SpringBootBlogAPI.git
    ```

2. **Navigate to the project directory:**

    ```sh
    cd SpringBootBlogAPI
    ```

3. **Check out the branch you want to work on:**

    ```sh
    git checkout <branch-name>
    ```

    For example, to switch to the `sbWeb` branch:

    ```sh
    git checkout sbWeb
    ```

4. **Build and run the project:**

    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

---

## Contributions

Feel free to fork this repository and make contributions. Please submit a pull request for any improvements or additions.

---

### Happy Coding!

