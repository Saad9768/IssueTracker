## 🧠 Technical Summary: Planning Service for Issue Tracker

---

### ✅ Technical Choices

- **Greedy First-Fit Scheduling Algorithm**  
  Chosen for its **simplicity, speed**, and practical efficiency. It quickly fits stories into weeks without complex computations, ensuring developers are loaded within capacity.
  
- **Spring Boot & H2 Database**  
  Fast prototyping and in-memory DB allowed quick iteration, especially valuable for integration tests.

- **DTO + Entity Separation**  
  Maintains clean architecture, separates web and persistence layers. Used `ModelMapper` for conversions.

- **Inheritance for Issue → Story**  
  Cleanly models shared fields (title, description, assignee) and lets `Story` extend `Issue`, following good OOP design.

- **Lombok + Validation Annotations**  
  Reduced boilerplate while ensuring input validation and safety.

---

## 🐞 Challenges Faced

- Designing the **assignment algorithm** while balancing developer workloads across weeks.
- Handling **edge cases** (e.g. oversized stories, no available developers).
- Setting up **integration tests** with proper mocking & in-memory DB (`@DataJpaTest`, `@SpringBootTest`).
- Structuring entities and **mapping inheritance** correctly in JPA.
- Swagger docs needed fine-tuning for readability and testing.

---

## ❌ Features Left Out (Consciously)

| Feature | Reason |
|--------|--------|
| Generic CRUD for `Bug` & `Story` | Prioritized algorithm over abstraction. Can easily be added with generics. |
| Skill-based or priority planning | Would require new schema fields, constraints, and more complex solvers. |
| Story reassignment logic | Out of scope but important in real agile environments. |
| Persistent story-week mapping | Only in-memory planning for now, no `Week` entity persisted. |
| Pagination/filtering | Needed for large datasets, but deferred to focus on core functionality. |

---

## 🔧 How I'd Improve with More Time

### 🌱 Functional Enhancements

- Add **generic base service/controller** for `Issue` types (`Bug`, `Story`).
- Include **developer constraints** (skills, workload history).
- Add **story priority handling** (e.g. high-priority stories get scheduled first).
- Allow **manual override** of planning results.

---

## 🚀 Future Scope & Best Practices

### ⚙️ Scalability & Performance
- **Rate Limiting**  
  Use Spring Cloud Gateway or Bucket4J to throttle requests per IP/user to prevent abuse.
  
- **Caching**  
  - Use `@Cacheable` for endpoints like `/stories`, `/developers`, `/bug`, `/plan`.
  - Backed by Redis for distributed environments.

- **Async Processing**  
  Schedule generation could be offloaded using `@Async` or a queue (e.g., RabbitMQ) for large datasets.

- **Pagination & Filtering**  
  Add Spring Data `Pageable` support for all `GET` endpoints, with filtering on status, assignee, etc.

### 🔒 Security
- Add **Spring Security** with:
  - **JWT Authentication**
  - Role-based access (e.g., `ADMIN`, `DEV`)
  - Endpoint protection (e.g., only `ADMIN` can delete)

### 🐳 Containerization

- **Docker**  
  Add Dockerfile for building a containerized version of the app:
  ```dockerfile
  FROM openjdk:17
  COPY target/issue-tracker.jar app.jar
  ENTRYPOINT ["java", "-jar", "/app.jar"]
  ```

- **Docker Compose**  
  Bundle Spring Boot + PostgreSQL/Redis using Docker Compose for local dev.

- **Kubernetes (K8s)**  
  Helm chart with deployment + service YAMLs:
  - Auto-scale pods based on CPU/memory
  - Use `ConfigMap` for properties (e.g., `points-per-developer-per-week`)
  - Health checks + rolling updates

### 📊 Monitoring

- Integrate with **Spring Boot Actuator**  
  Expose health, metrics, beans, env.
- Use **Prometheus + Grafana** dashboards.
- Use **ELK** (Elasticsearch + Logstash + Kibana) or **Loki** for logs.

---

## 📎 Swagger Access

Accessible at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
API spec allows testing story CRUD, developer creation, and weekly plan generation.
