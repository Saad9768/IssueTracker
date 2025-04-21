# 🛠️ Issue Tracker Application

A Spring Boot-based issue tracking system to manage software development stories, assign them to developers, and auto-generate weekly work plans.

---

## 📦 Features

- 🔍 CRUD operations for `Story` (issue)
- 👤 Developer management
- ✅ Status tracking (`NEW`, `IN_PROGRESS`, `DONE`)
- 📅 Weekly planning based on story points and developer capacity
- 🔍 Interactive API documentation (Swagger)
- 🧪 Integration & unit tests
- 🛡️ Error handling and validations

---

## 🧰 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 (test) / MySQL/PostgreSQL (prod)
- Maven
- JUnit 5 / Mockito
- Lombok
- Swagger (SpringDoc OpenAPI)
- ModelMapper (for DTO conversion)

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- IDE (IntelliJ, Eclipse, VSCode)

---

### 🔧 Setup & Run

#### 1. Clone the repo

```bash
git clone https://github.com/your-org/issue-tracker.git
cd issue-tracker
```

#### 2. Configure application properties

```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console.enabled: true
  jpa:
    hibernate.ddl-auto: update
    show-sql: true

points-per-developer-per-week: 10
```

#### 3. Run the app

```bash
mvn spring-boot:run
```

Then visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Running Tests

```bash
mvn clean test
```

Includes:

- ✅ Unit tests
- 🔁 Integration tests (via MockMvc)
- ⚙️ Uses H2 in-memory DB for fast and isolated testing

---

## 📬 API Endpoints

> Full API reference available via [Swagger UI](http://localhost:8080/swagger-ui/index.html)

### 🔹 Story

| Method | Endpoint             | Description           |
|--------|----------------------|-----------------------|
| GET    | `/api/stories`       | Get all stories       |
| GET    | `/api/stories/{id}`  | Get story by ID       |
| POST   | `/api/stories`       | Create a new story    |
| PUT    | `/api/stories/{id}`  | Update existing story |
| DELETE | `/api/stories/{id}`  | Delete story          |
| GET    | `/api/stories/plan`  | Generate weekly plan  |

---

## 📅 Weekly Planning Logic

- Each developer has a configurable point capacity per week.
- Stories are distributed across weeks to avoid overloading any developer.
- If a story doesn't fit into the current week, a new one is created.

---

## 📁 Project Structure

```
.
├── controller
├── dto
├── entity
├── repository
├── service
│   ├── impl
├── exception
├── configuration
└── test
```

---

## 📚 API Documentation (Swagger)

Interactive API docs available at:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## ✨ Author

Made with ❤️ by Saad Khatimiti