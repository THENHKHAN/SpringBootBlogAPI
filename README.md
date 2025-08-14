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

I am actively working on features like, logging, and automated tests to improve robustness.


---

## 🚀 Why Pagination and Sorting Matter

When a blog API grows to contain thousands of posts, returning all posts in one response becomes inefficient and slow. Pagination allows clients to request data in chunks (pages), reducing response size and improving performance.

Sorting lets users order posts by criteria such as newest first, alphabetical titles, or author name, enhancing usability.

This implementation leverages Spring Data JPA’s built-in support for `Pageable` and `Sort` objects to efficiently query and deliver paged and sorted data, a crucial feature for scalable backend systems.

---

## 🚦 API Routes and Endpoints

The API exposes RESTful endpoints for managing blog posts and users. Below is a summary of key routes along with their purpose and expected input/output.

| HTTP Method | Route                  | Description                                  | Request Body / Params                       | Response                          |
|-------------|------------------------|----------------------------------------------|--------------------------------------------|----------------------------------|
| `POST`      | `/api/posts`           | Create a new blog post                        | JSON `{ "title": "...", "content": "...", "authorId": ... }` | Created blog post object          |
| `GET`       | `/api/posts`           | Retrieve all blog posts (supports pagination & sorting) | Query params: `page`, `size`, `sort` (e.g., `?page=0&size=10&sort=createdAt,desc`) | Paginated list of blog posts     |
| `GET`       | `/api/posts/{id}`      | Retrieve a single blog post by its ID        | Path param: `id`                           | Blog post object or 404 if not found |
| `PUT`       | `/api/posts/{id}`      | Update an existing blog post                  | Path param: `id`; JSON with updated fields | Updated blog post object          |
| `DELETE`    | `/api/posts/{id}`      | Delete a blog post                            | Path param: `id`                           | 204 No Content on success         |
| `POST`      | `/api/auth/register`   | Register a new user                           | JSON `{ "username": "...", "password": "...", "email": "..." }` | User object or success message    |
| `POST`      | `/api/auth/login`      | Authenticate user and receive JWT token      | JSON `{ "username": "...", "password": "..." }` | JWT token                         |

---

### Endpoint Details:

- **Create Post (`POST /api/posts`)**  
  Accepts a JSON payload containing `title`, `content`, and `authorId`.  
  Validates input and creates a new blog post in the database. Returns the created post with its ID and timestamps.

- **Get Posts (`GET /api/posts`)**  
  Supports query parameters for **pagination** (`page`, `size`) and **sorting** (`sort`).  
  Returns a paginated response containing posts and metadata like total pages, total elements, and current page number.

- **Get Post by ID (`GET /api/posts/{id}`)**  
  Returns the blog post with the specified ID.  
  Returns a 404 error if the post does not exist.

- **Update Post (`PUT /api/posts/{id}`)**  
  Updates fields of the blog post identified by `id`.  
  Validates ownership or permissions before updating.

- **Delete Post (`DELETE /api/posts/{id}`)**  
  Deletes the blog post with the given ID.  
  Requires appropriate authorization. Returns HTTP 204 on success.

- **User Registration (`POST /api/auth/register`)**  
  Registers a new user with username, password, and email.  
  Passwords are securely hashed before storing.

- **User Login (`POST /api/auth/login`)**  
  Authenticates user credentials and returns a JWT token for session management.

---

### Notes:

- All endpoints that modify data (`POST`, `PUT`, `DELETE`) are protected and require a valid authentication token.
- The API uses standard HTTP status codes for success and error responses.
- Input validation is enforced on all request bodies to ensure data consistency and security.

---

## 🔒 Security

This API implements robust security features to protect sensitive operations and user data.

### Authentication

- Uses **JWT (JSON Web Tokens)** for stateless authentication.  
- Upon successful login (`POST /api/auth/login`), the server issues a JWT token.  
- The client includes this token in the `Authorization` header (`Bearer <token>`) of subsequent requests.

### Authorization and Role-Based Access Control (RBAC)

- Protected routes (`POST`, `PUT`, `DELETE` on `/api/posts`, etc.) require a valid JWT token.  
- The system supports **role-based access control**, distinguishing between roles such as **admin** and **regular users**.  
- For example:
  - **Admins** have permissions to manage all posts and users.
  - **Regular users** can only create, update, or delete their own posts.
- Role enforcement is done both at the API route level and within the service layer for secure and fine-grained control.

### Password Handling

- User passwords are **hashed using strong algorithms (e.g., BCrypt)** before storage to ensure security.

### Exception Handling for Security

- Unauthorized or invalid token requests return HTTP status `401 Unauthorized`.  
- Access denied attempts due to insufficient roles return HTTP status `403 Forbidden`.

### Future Enhancements (Planned)

- Refresh token mechanism for better token lifecycle management  
- Rate limiting to prevent abuse  

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



