# JwtAuthApp-KotlinReact
A secure single-page app showcasing JWT authentication, built with Spring Boot + Kotlin backend and React + TypeScript frontend. Includes login flow, protected API access, data management, and export functionality.

## Version info
Currently this project has a Spring Boot + Kotlin backend base and React + TypeScript frontend base. Everything else is still a work in progress.

## Installation & Setup

1. **Backend**:

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/backend
./gradlew clean build
./gradlew bootRun
```

2. **Frontend**:

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/frontend
npm install
npm start
```

3. **Result**:

Frontend is served at: http://localhost:3000
Backend is available at: http://localhost:8080

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

## Running tests

For these both backend and frontend should both be already running

**JwtUtilsTest**

```bash
cd /opt/kjc/int/JwtAuthApp-KotlinReact/backend
./gradlew clean
./gradlew test --tests com.katariinatuulia.backend.JwtUtilsTest
```

## Versions

Java 21, Kotlin 1.9.25, Spring Boot 3.4.5, React 19.1., Typescript 4.9.5