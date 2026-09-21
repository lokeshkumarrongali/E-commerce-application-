# SpringEcom — E-Commerce REST API

A **Spring Boot** backend REST API for an e-commerce platform, supporting full product management (with image uploads) and order placement with automatic stock management.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.1 |
| Web | Spring MVC (spring-boot-starter-webmvc) |
| ORM | Spring Data JPA (Hibernate) |
| Database | PostgreSQL |
| Boilerplate | Lombok |
| Build Tool | Maven |

---

## 📁 Project Structure

```
SpringEcom/
├── src/
│   ├── main/
│   │   ├── java/com/example/SpringEcom/
│   │   │   ├── controller/
│   │   │   │   ├── HelloController.java       # Health check endpoint
│   │   │   │   ├── ProductController.java     # Product CRUD + image + search
│   │   │   │   └── OrderController.java       # Order placement & listing
│   │   │   ├── model/
│   │   │   │   ├── Product.java               # Product entity (with image storage)
│   │   │   │   ├── Order.java                 # Order entity
│   │   │   │   ├── OrderItem.java             # Order line item entity
│   │   │   │   └── dto/
│   │   │   │       ├── OrderRequest.java
│   │   │   │       ├── OrderResponse.java
│   │   │   │       ├── OrderItemRequest.java
│   │   │   │       └── OrderItemResponse.java
│   │   │   ├── Service/
│   │   │   │   ├── ProductService.java        # Product business logic
│   │   │   │   └── OrderService.java          # Order + stock management
│   │   │   ├── Repo/
│   │   │   │   ├── ProductRepo.java           # Product JPA repository
│   │   │   │   └── OrderRepo.java             # Order JPA repository
│   │   │   └── SpringEcomApplication.java     # Application entry point
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/SpringEcom/
│           └── SpringEcomApplicationTests.java
├── pom.xml
└── README.md
```

---

## 🗄️ Database Configuration

Configure your PostgreSQL connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/telusko
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> **Note:** Update `username`, `password`, and the database name to match your local PostgreSQL setup before running.

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL running locally on port `5432`

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/lokeshkumarrongali/E-commerce-application-.git
   cd SpringEcom
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE telusko;
   ```

3. **Update credentials** in `src/main/resources/application.properties`

4. **Build and run**
   ```bash
   # Linux / macOS
   ./mvnw spring-boot:run

   # Windows
   mvnw.cmd spring-boot:run
   ```

5. The API will be available at: `http://localhost:8080`

---

## 📦 Data Models

### Product

| Field | Type | Description |
|---|---|---|
| `id` | Integer | Auto-generated primary key |
| `name` | String | Product name |
| `description` | String | Product description |
| `brand` | String | Brand name |
| `price` | BigDecimal | Product price |
| `category` | String | Product category |
| `releaseDate` | Date | Release date (`yyyy-MM-dd`) |
| `productAvailable` | boolean | Availability flag |
| `stockQuantity` | int | Available stock count |
| `imageName` | String | Image file name |
| `imageType` | String | Image MIME type |
| `imageData` | byte[] | Binary image data (LOB) |

### Order

| Field | Type | Description |
|---|---|---|
| `id` | Long | Auto-generated primary key |
| `orderId` | String | Unique order ID (e.g., `ORD-A1B2C`) |
| `customerName` | String | Customer name |
| `email` | String | Customer email |
| `status` | String | Order status (e.g., `PLACED`) |
| `orderDate` | LocalDate | Date the order was placed |
| `orderItems` | List\<OrderItem\> | Line items in the order |

### OrderItem

| Field | Type | Description |
|---|---|---|
| `id` | int | Auto-generated primary key |
| `product` | Product | Associated product |
| `quantity` | int | Quantity ordered |
| `totalPrice` | BigDecimal | `price × quantity` |

---

## 🔌 API Endpoints

### Products — `/api`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/product/{id}` | Get a product by ID |
| `GET` | `/api/product/{id}/image` | Get product image (binary) |
| `GET` | `/api/products/search?keyword=` | Search products by keyword |
| `POST` | `/api/product` | Add a new product (with optional image) |
| `PUT` | `/api/product/{id}` | Update an existing product |
| `DELETE` | `/api/product/{id}` | Delete a product by ID |

#### Add / Update Product — `multipart/form-data`

```
product (JSON part):
{
  "name": "Laptop",
  "description": "High performance laptop",
  "brand": "Dell",
  "price": 75000.00,
  "category": "Electronics",
  "releaseDate": "2024-01-15",
  "productAvailable": true,
  "stockQuantity": 50
}

imageFile: (binary image file, optional)
```

---

### Orders — `/api`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders/place` | Place a new order |
| `GET` | `/api/orders` | Get all orders |

#### Place Order — Request Body (`application/json`)

```json
{
  "customerName": "Lokesh Kumar",
  "email": "lokesh@example.com",
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

#### Place Order — Response

```json
{
  "orderId": "ORD-A1B2C",
  "customerName": "Lokesh Kumar",
  "email": "lokesh@example.com",
  "status": "PLACED",
  "orderDate": "2026-09-21",
  "items": [
    { "productName": "Laptop", "quantity": 2, "totalPrice": 150000.00 },
    { "productName": "Mouse",  "quantity": 1, "totalPrice": 500.00 }
  ]
}
```

> **Note:** Placing an order automatically **decrements the stock** for each ordered product.

---

## 🔑 Key Features

- ✅ Full **CRUD** for products
- 🖼️ **Image upload & retrieval** — stored as binary (LOB) in the database
- 🔍 **Keyword search** across products
- 🛒 **Order placement** with auto-generated unique order IDs (`ORD` + UUID prefix)
- 📦 **Automatic stock decrement** on order placement
- 🔗 **CORS enabled** on all endpoints (`@CrossOrigin`)
- 📋 **DTO pattern** for clean request/response separation

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📄 License

This project is open-source and available for educational purposes.
