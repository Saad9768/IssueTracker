### 🧠 **Technical Design Choices & Rationale**

#### 1. **Spring Boot Framework**
- **Why?** It provides rapid setup, robust auto-configuration, and is ideal for REST APIs and layered architectures.
- **Benefits:** Production-ready defaults, integrated testing support, and simplified dependency management with Spring Starter packs.

---

#### 2. **Layered Architecture**
- **Controller → Service → Repository**, with DTOs and Mappers in between.
- **Why?** Encourages separation of concerns, making the codebase modular, testable, and scalable.

---

#### 3. **DTO Pattern**
- **Why?** To decouple the internal entity models (`Story`, `Developer`) from the data exposed externally via the API.
- **Tool:** DTO conversion via `DtoConverter` (e.g., MapStruct or ModelMapper) for clean mapping.

---

#### 4. **Planning Algorithm**
- **Goal:** Efficiently assign stories across multiple weeks based on developer capacity.
- **Logic:** `WeekPlan` objects encapsulate week-specific developer assignments and story limits.
- **Why?** This ensures:
  - No developer is overbooked
  - Stories are evenly distributed across weeks
  - Future scalability (e.g., support vacations, partial capacities)

---

#### 5. **Validation & Exception Handling**
- Used:
  - `@Valid`, `@NotNull`, `@Min`, `@NotBlank` for entity/DTO validation
  - Custom exception handling via `ResourceNotFoundException`
- **Why?** To ensure input data integrity and provide meaningful error responses.

---

#### 6. **Swagger Integration**
- **Why?** Enables auto-generated interactive API documentation via OpenAPI, improving testability and onboarding for other developers or testers.
- **URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

#### 7. **In-Memory H2 for Testing**
- **Why?** Lightweight, fast, and perfect for isolated integration tests using MockMvc and SpringBootTest.

---

#### 8. **Logging with SLF4J**
- **Why?** To trace operations (e.g., story assignment, planning generation) and simplify debugging.

---

#### 9. **Configuration via `application.yml`**
- Used `@Value("${points-per-developer-per-week}")` to externalize point limits.
- **Why?** Encourages configurability across environments (dev, test, prod).
