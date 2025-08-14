# Spring Boot Learning With SpringBootBlogAPI 🚀

Welcome to the **Spring Boot Learning With SpringBootBlogAPI** repository!  
This project explores various aspects of the `Spring Boot` Java framework through mini-projects and exercises. Each branch represents a feature or concept implemented in Spring Boot, ranging from basic CRUD operations to advanced security and validation mechanisms.

---

## 📸 Swagger UI - API Exposed

Check out the Swagger UI screenshots for the exposed API endpoints:  
[Swagger UI Screenshot 1](https://github.com/THENHKHAN/SpringBootBlogAPI/tree/main/springboot-blog-restapi/Imp_SS_EndPointsCLient/17.1_SwaggerUI_exposes_.png)  
[Swagger UI Screenshot 2](https://github.com/THENHKHAN/SpringBootBlogAPI/blob/main/springboot-blog-restapi/Imp_SS_EndPointsCLient/17.2_SwaggerUI_exposes_.png)

---

## 🏗 Architecture and Design

This project implements a Blog API backend using **Spring Boot**, following a layered architecture pattern to ensure scalability and maintainability.

### High-Level Architecture

- **Controller Layer:** Handles incoming HTTP requests, validates inputs, and returns responses.
- **Service Layer:** Contains core business logic for managing blog posts (CRUD operations).
- **Repository Layer:** Interfaces with the database using Spring Data JPA.
- **Security Layer:** Manages user authentication and authorization.
- **Exception Handling:** Global handlers provide consistent error responses.

### Low-Level Design

Key classes and their responsibilities:

| Class              | Responsibility                                                  |
|--------------------|-----------------------------------------------------------------|
| `BlogPost`         | Represents blog posts with title, content, author, timestamps. |
| `User`             | Represents users with authentication data.                      |
| `BlogPostService`  | Business logic for creating, reading, updating, deleting posts.|
| `BlogPostRepository`| Database access layer using Spring Data JPA.                   |
| `ExceptionHandler` | Custom exception handling (e.g., `PostNotFound`).               |

### Learning and Ownership

This project was initially built following a Udemy course to learn Spring Boot fundamentals. Since then, I have extended it by adding:

- Custom exception handling  
- Input validation  
- Basic authentication  
- Thoughtful API structure following best practices

I am actively working on features like pagination, logging, and automated tests to improve robustness.

---

## 🚀 Getting Started

Follow these steps to get the project up and running:

1. **Clone the repository:**

    ```bash
    git clone https://github.com/THENHKHAN/SpringBootBlogAPI.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd SpringBootBlogAPI
    ```

3. **Checkout the branch you want to work on:**

    ```bash
    git checkout <branch-name>
    ```

    Example:

    ```bash
    git checkout sbWeb
    ```

4. **Build and run the project:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## 🤝 Contributions

Feel free to fork this repository and make improvements or add features.  
Please submit a pull request for any changes you want to contribute.

---

### Happy Coding! 🎉
