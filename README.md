# 💱 Currency API for Demo

This project provides a RESTful API to manage currencies and fetch exchange rate data from the OANDA public API. Built with **Spring Boot 3**, **Java 17**, **H2**, and supports modern features like Swagger, scheduled sync, AES/RSA, and Docker.

---

## 🔧 Tech Stack

- Java 17
- Spring Boot 3.x
- Maven
- Spring Web + Spring Data JPA
- H2 In-Memory Database
- Swagger/OpenAPI
- AES/RSA encryption (via BouncyCastle)
- JUnit 5 + Mockito
- Docker Support

## ✅ Core Features

| Feature                           | Description                               |                                
|---------------------------------- |-------------------------------------------------------------|  
| 🗃 Currency DB maintenance        | CRUD APIs for managing currency codes and names             |  
| 🌐 External API integration      | Calls OANDA exchange rate API for latest currency data       |  
| 🔁 Data transformation           | Converts external API to new internal format with update time|                
| 🕒 Scheduled synchronization     | Periodically pulls latest exchange data via scheduler        |                      
| 📋 Sorted query by currency code | List curencies sorted by `code`                             |  
| 🧪 Unit tests                    | JUnit tests for service logic                                |

Note: You can access h2-console with link: `http://localhost:8080/h2-console`

## ✨ Optional Features Included

| Feature                   | Status        | Details                                       |  
|-------------------------  |-------------  |-----------------------------------------------|  
| 📦 Swagger UI            | ✅ Enabled    | `/swagger-ui.html`                           |  
| 🧾 API Logging           | ✅ Included   | Implement to add with logger                   |  
| 🌐 i18n Design           | ✅ Included   | Optional to add with `messages.properties`   |  
| 🧰 Design Patterns (2+)  | ✅ Used       | Factory (DTO), Strategy (Scheduler)            |  
| 🐳 Docker Support        | ✅ Included   | Dockerfile with port `8080` exposed           |  
| 🛡 Global Error Handling | ✅ Included   | Unified JSON error response                    |  
| 🔐 AES/RSA Encryption    | ✅ AES only   | Encryption utilities included                  |  

Note: You can access Swagger UI with link: `http://localhost:8080/swagger-ui/index.html`

## 🚀 How to Run

### 🔨 With Maven (Local)
```bash
# Package the app
mvn clean install

# Run the app
mvn spring-boot:run

```

### 🔨 With Docker
```bash
# Build JAR
mvn clean package

# Build Docker image
docker build -t currency-api .

# Run container
docker run -p 8080:8080 currency-api

```

### 🔨 With Docker Compose
```bash
# Build and Run application
docker-compose up --build



