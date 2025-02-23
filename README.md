# Products API - Microservice Architecture with Docker and Keycloak

This project is designed to learn and implement **Docker** and **Keycloak** by developing a microservice-based **Products API**. It follows best practices in API design, security, and scalability.

---

## 🚀 Phase 1: Initial Microservice Architecture  

### 1️⃣ API Gateway  
- Acts as the **entry point** for all clients.  
- Implements **JWT authentication** for securing endpoints..  

### 2️⃣ Product Setup Service (Handles Insert, Update, Delete, and Retrieve Product Operations)  
- Built using **Spring Boot** and **Spring Data JPA**.  
- Implements **Spring Validation** for request validation.  
- Integrated with **Swagger** for API documentation.  
- Supports **pagination** for efficient data retrieval.  
- Includes **unit tests** to ensure reliability.  
- **Logging** is added for better monitoring and debugging.  
- Uses **GlobalExceptionHandler** to handle application-wide exceptions.  
- Implements **message queuing with RabbitMQ** for product events:  
  - `MESSAGE_QUEUE_PRODUCT_ADD` – Triggered after adding a product.  
  - `MESSAGE_QUEUE_PRODUCT_UPDATE` – Triggered after updating a product.  
  - `MESSAGE_QUEUE_PRODUCT_DELETE` – Triggered after deleting a product.  
- Secured with **Spring Security**.  

### 3️⃣ Product Search Service (Handles Product Search Operations)  
- **Does not connect to a database**; fetches data from the **Product Setup Service**.  
- Listens to RabbitMQ queues to keep the product cache up to date:  
  - `MESSAGE_QUEUE_PRODUCT_ADD` – Updates cache after adding a product.  
  - `MESSAGE_QUEUE_PRODUCT_UPDATE` – Updates cache after modifying a product.  
  - `MESSAGE_QUEUE_PRODUCT_DELETE` – Updates cache after deleting a product.  
- Uses an **in-memory cache (`PRODUCTS_MAP`)** to store frequently accessed data for better performance.  
- The number of instances of this service **scales dynamically** based on traffic and usage statistics.  
- Secured with **Spring Security**.  

### 4️⃣ Common Library  
- A shared library that contains common utilities, DTOs, and configurations for reuse across microservices.  

### 5️⃣ Discovery Server  
- Uses **Eureka** for service discovery to enable microservices to locate and communicate with each other dynamically.  

### 6️⃣ Dockerization  
- The entire project is **containerized using Docker** for seamless deployment and scalability.  

---

## 📌 Phase 2: Enhancements and Additional Features  

- ✅ **Replace JWT with Keycloak** – Migrate authentication from **JWT-based security** to **Keycloak-based identity management**.  
- ✅ **Improved Product Details Handling** – Use **basic JPA annotations** for enhanced data modeling.  
- ✅ **Detailed Product Search Results** – Introduce more detailed response structures.  
- ✅ **Redis Caching** – Implement **Redis cache** for better performance and reduced database queries.  

---

### 🛠️ **Technologies Used**
- **Spring Boot** (REST API Development)  
- **Spring Data JPA** (Database Management)  
- **Spring Security** (Authentication & Authorization)  
- **Keycloak** (Identity and Access Management)  
- **RabbitMQ** (Message Queue for Event Handling)  
- **Eureka Discovery** (Service Registry)  
- **Swagger** (API Documentation)  
- **Docker** (Containerization)  
- **Redis (Upcoming Feature)** (Caching for Performance Optimization)  

---

## 📜 **How to Run the Project**  
1. Clone the repository:  
   ```bash
   git clone https://github.com/dasunSenadeera/Products-API---Microservice-Architecture-with-Docker-and-Keycloak
