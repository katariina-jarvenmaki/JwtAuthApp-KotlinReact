# JwtAuthApp-KotlinReact
A secure single-page app showcasing JWT authentication, built with Spring Boot + Kotlin backend and React + TypeScript frontend with postgre database. Includes login flow and protected API access.

```bash
APP: kjc-int  
DIRECTORY STRUCTURE: /opt/kjc/int/JwtAuthApp-KotlinReact
```

## Installation & Setup

1. **Setup Postegre**:

On terminal:

```bash
cd /opt/kjc/int/JwtAtuhApp-KotlinReact/backend
sudo -i -u postgres
psql
```

On Postgre-console:

```bash
CREATE USER usr_jwtauthapp_kotlinreact WITH PASSWORD 'TDen}Giv{S8q';
CREATE DATABASE jwtauthapp_kotlinreact OWNER usr_jwtauthapp_kotlinreact;
GRANT ALL PRIVILEGES ON DATABASE jwtauthapp_kotlinreact TO usr_jwtauthapp_kotlinreact;
```

2. **Backend**:

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/backend
./gradlew clean build
./gradlew bootRun
```

3. **Frontend**:

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/frontend
npm install
npm start
```

4. **Result**:

Frontend is served at: http://localhost:3000
Backend is available at: http://localhost:8080

## Test Accounts

```bash 
Username: admin     Password: admin123
```

## Using API with curl

Navigate to backend folder:
```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/backend/
```

Request token:
```bash
curl http://localhost:8080/api/public/token -w "\n"
```

Trying to access without token:
```bash
curl http://localhost:8080/api/secure/data -w "\n"
```

Access with token:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" http://localhost:8080/api/secure/data -w "\n"
```

Test login with curl:
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin123"}' -w "\n"
```

Trying login with invalid credentials:
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username": "user", "password": "pass"}' -w "\n"
```

## Running tests

For these both backend and frontend should both be already running

**JwtUtilsTest**

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/backend
./gradlew clean
./gradlew test --tests com.katariinatuulia.backend.JwtUtilsTest
```

## Architecture Overview

**Backend**

* Java 21

* Spring Boot 3.4.5 + Kotlin 1.9.25

* JWT-based auth & middleware logic

* REST API endpoints:
  * GET /api/public/token
  * GET /api/secure/data 

* PostgreSQL (with JPA)

* Faker-generated mock data used in early phase

**Frontend**

* React 19.1 + TypeScript 4.9.5

* SPA architecture with React Router

* Axios for API communication

## Folder structure

```
/opt/kjc/int/JwtAuthApp-Kotlin/
├── architecture/
├── backend/src/
│   ├── main/kotlin/com/katariinatuulia/backend/
│   │   ├── jwt_auth
│   │   │   ├── AuthController.kt
│   │   │   ├── AuthService.kt
│   │   │   ├── CorsConfig.kt
│   │   │   ├── DataInitializer.kt
│   │   │   ├── JwtFilter.kt
│   │   │   ├── JwtSecurityConfig.kt
│   │   │   ├── JwtUtils.kt
│   │   │   ├── LoginRequest.kt
│   │   │   ├── SecureController.kt
│   │   │   ├── User.kt
│   │   │   └── UserRepository.kt
│   │   ├── BackendApplication.kt
│   │   └── HomeContainer.kt
│   └── test/kotlin/com/katariinatuulia/backend/
│       ├── BackendApplicationTests.kt
│       └── JwtUtilsTest.kt
├── frontend/src/
│   ├── components/
│   │   └── LoginForm.tsx
│   └── pages/
│       ├── Dashboard.tsx   
│       └── Home.tsx
├── usage-examples/
└── README.md
```

## Diagram

Frontend SPA  →  Auth Form  →  JWT  →  Dashboard  
     ↓                                       ↑  
 Backend (REST API) ← Token Middleware ← Spring Security  

## Requirements Checklist

**Phase 1 – Setup**

* Backend: Spring Boot + Kotlin ✔️
* Frontend: React + TS ✔️
* PostgreSQL + JPA ✔️
* JWT authentication ✔️
* REST API structure ✔️
* Git project init ✔️

**Phase 2 – Backend Implementation** 

* JWT users on Postgre ✔️
* /login → token ✔️

**Phase 3 – Frontend Implementation** 

* Login view ✔️
* Dashboard view ✔️
* Token expiration handling ✔️
* User greeting ("Hello, user!") ✔️

**Phase 4 – Testing & Docs** 

* Backend: JUnit test ✔️
* README documentation ✔️

## Troubleshooting

If port 8080 is already in use:
```bash
sudo lsof -i :8080
sudo kill -9 <PID>
```

## License

MIT © 2025
