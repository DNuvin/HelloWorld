# 📘 Hello World App

A production-style Spring Boot REST API that demonstrates clean architecture, validation, standardized responses, centralized exception handling, structured logging with trace IDs, containerization, and deployment readiness.

---

# 🚀 Features

✔ RESTful API design
✔ Clean layered architecture
✔ Request validation (Jakarta Validation)
✔ Centralized exception handling
✔ Standardized API response wrapper
✔ Traceable logging with MDC Trace IDs
✔ Docker containerization
✔ Unit & integration testing
✔ Production-ready packaging

---

# 🧱 Architecture Overview

The application follows **Layered Clean Architecture** principles:

```
Client → Controller → Service → Domain → Response Transformer
                ↓
         Global Exception Handler
```

### 🔹 Layers

**Adapter Layer**

* Handles HTTP requests
* Validates inputs
* Calls application services
* Transforms domain responses

**Application Layer**

* Contains business logic (Services)
* Processes validated inputs
* Produces domain models

**Domain Layer**

* Core business objects
* Independent of frameworks

**Infrastructure Layer**

* Exception handling
* Logging
* Response building utilities

---

# 🏗️ Architecture Diagram (Logical)

```
[ Client ]
     ↓
[ REST Controller ]
     ↓
[ Application Service ]
     ↓
[ Domain Model ]
     ↓
[ Response Transformer ]
     ↓
[ API Response ]
```

Cross-cutting:

```
✔ Global Exception Handler
✔ Logging System (Trace IDs)
✔ Validation Framework
```

---

# ⚙️ Prerequisites

Install the following:

* ☕ Java 17+
* 📦 Maven 3.8+
* 🐳 Docker (optional)

---

# ▶️ Run Application (Without Docker)

### 1️⃣ Build JAR

```bash
mvn clean package
```

Generated file:

```
target/hello-world-app.jar
```

### 2️⃣ Run Application

```bash
java -jar target/hello-world-app.jar
```

App starts at:

```
http://localhost:8080
```

---

# 🐳 Run Application (With Docker)

### 1️⃣ Build Docker Image

```bash
docker build -t hello-world-app .
```

### 2️⃣ Run Container

```bash
docker run -p 8080:8080 --name hello-world-app hello-world-app
```

App runs at:

```
http://localhost:8080
```

---

# 🧪 Running Tests

### Run all tests

```bash
mvn test
```

### Clean + full verification

```bash
mvn clean verify
```

---

# 📂 Project Structure

```
src/main/java
 ├── adapter        → Controllers & DTOs
 ├── application    → Business services
 ├── domain         → Core models
 └── infrastructure → Exception handling & utilities

src/test/java       → Test classes
Dockerfile          → Docker config
pom.xml             → Maven dependencies
README.md           → Documentation
```

---

# 🔌 API Usage

## ✅ Request

```
GET /hello-world?name=Alice
```

## ✅ Success Response

```json
{
  "success": true,
  "code": "GEN-SUC-200",
  "message": "Success",
  "data": {
    "message": "Hello Alice"
  },
  "errors": [],
  "timestamp": "2026-03-09T23:31:46.673997674"
}
```

## ❌ Validation Error

```json
{
  "success": false,
  "code": "GEN-VAL-400",
  "message": "Validation failed",
  "data": null,
  "errors": [
    {
      "field": "name",
      "message": "Name is required"
    },
    {
      "field": "name",
      "message": "Invalid Input"
    }
  ],
  "timestamp": "2026-03-10T04:43:09.292979"
}
```

---

# 🧠 Design Decisions

### ✅ Why Layered Architecture?

Improves maintainability, testability, and separation of concerns.

### ✅ Why Global Exception Handling?

Ensures consistent API error responses and prevents controller clutter.

### ✅ Why Response Wrapper & Standardized Error Codes?

Provides standardized API contract across all endpoints.
Internal error codes are mapped systematically to provide clarity for consumers and easier debugging.
Makes integration and client-side handling simpler and predictable.

### ✅ Why Trace ID Logging?

Enables request tracking across services in distributed systems.


### ✅ Why Manual DTO Validation?

Allows custom validation logic and cleaner controller code.

---

# 📈 Scaling Strategy

The application is stateless and horizontally scalable.

# 🧾 Assumptions Made

* Java 17 runtime
* Default port: 8080
* Stateless REST APIs
* No authentication required
* Suitable for demo & production extension
* Docker uses lightweight JDK image

---

# 👨‍💻 Developer Information

**Author:** Dewuruge Nuvin Samadhi Kalpadeep
**Project Type:** Spring Boot REST API Assignment
**Build Tool:** Maven
**Packaging:** Executable JAR
**Containerization:** Docker

---

# ✅ Summary

This project demonstrates how to build a production-ready Spring Boot REST API with:

* Clean architecture
* Proper validation
* Structured error handling
* Standard API contracts
* Observability via logging
* Containerized deployment
* Testable design

---

⭐ Ready for local execution, container deployment, and cloud scaling.
