# 🌿 Antara App – Spring Boot Backend

Welcome to the **Antara App Backend**, the Spring Boot-powered REST API that fuels the Antara wellness platform. Built with production-grade practices, this backend supports secure user management, contact form submission, and full CRUD capabilities.

> 🔗 Frontend Repo: [Antara-App-React](https://github.com/dineshvijayakumar96/Antara-App-React)

> 🌐 Live API Base URL: [`https://antara-application-latest.onrender.com/api/`](https://antara-application-latest.onrender.com/api/)

---

## 🚀 Features

- ✅ **RESTful API** architecture
- 🔐 **JWT-based Authentication**
- 👥 **Role-based Access Control**
- 📬 **Contact Form Data Submission + Management**
- 🔁 **CRUD Operations** on key entities
- ✅ Supports **pagination**, **filtering**, and is **React Admin-compatible**
- 🌍 **CORS configured** for frontend access
- 🐘 PostgreSQL / 🐬 MySQL compatible
- ☁️ **Deployed on Render**

---

### 📧 Email Notifications
- Dynamic email generation using **Thymeleaf**
- Sends:
    - Admin notification on new submission
    - Acknowledgment to the user

---

### 🧠 Admin Dashboard Integration
- APIs structured to support [React Admin](https://marmelab.com/react-admin)
- Headers and formats aligned for smooth integration

---

## 📦 Tech Stack

| Layer           | Tech Used                  |
|-----------------|----------------------------|
| Language        | Java 17                    |
| Framework       | Spring Boot 3              |
| API Design      | Spring MVC / REST          |
| Security        | Spring Security + JWT      |
| ORM & DB        | Spring Data JPA + JPA      |
| Email           | JavaMailSender + Thymeleaf |
| Database        | PostgreSQL                 |
| Deployment      | Render                     |
| Dev Tools       | Maven, Spring DevTools     |

---

## 🔐 Authentication & Authorization

- JWT is used for stateless token-based authentication
- Spring Security restricts endpoints by role (USER, ADMIN)
- Tokens must be sent via Authorization: Bearer <token> header

---

## 🗂 Project Structure

````
📦 antara-app-spring-boot
├── src
│   ├── main
│   │   ├── java/com/antara
│   │   │   ├── controller/               # Handles REST API endpoints
│   │   │   ├── dao/                      # Spring Data JPA repositories
│   │   │   ├── entity/                   # Entity and DTO classes
│   │   │   ├── security/                 # Security and general config
│   │   │   ├── service/                  # Business logic layer
│   │   │   └── AntaracaresApplication    # Main application
│   └── resources
│       ├── templates/                    # Thymeleaf HTML for emails
│       └── application.properties        # App configuration
├── pom.xml                               # Maven project descriptor
````

## 📚 Learning Highlights

This backend showcases:
- Structuring scalable REST APIs
- Secure authentication using JWT
- Handling data persistence with Spring Data JPA
- Environment variable configuration and cloud deployment