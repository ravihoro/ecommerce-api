# E-commerce Backend

A RESTful e-commerce backend built with **Spring Boot** and **PostgreSQL**, providing authentication, product catalog, search, cart, and favorites APIs for a Flutter e-commerce application.

## Features

* JWT Authentication with Access & Refresh Tokens
* User Authentication (`/auth/login`, `/auth/refresh`, `/auth/me`)
* Product Listing with Pagination
* Product Details
* Product Search using PostgreSQL
* Category-based Product Listing
* Shopping Cart Management
* Favorites (Wishlist) Management
* Layered Architecture (Controller, Service, Repository)

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* JWT Authentication

## Project Structure

```text
src
├── controller
├── dto
├── entity
├── mapper
├── repository
├── service
└── config
```

## Database

The application uses PostgreSQL with the following entities:

* Users
* Refresh Tokens
* Categories
* Products
* Product Images
* Reviews
* Cart
* Cart Items
* Favorites

Product data is imported from DummyJSON.

## API Endpoints

### Authentication

```http
POST   /auth/login
POST   /auth/refresh
GET    /auth/me
```

### Products

```http
GET    /products
GET    /products/{id}
GET    /products/category/{slug}
GET    /products/search?q=
```

### Categories

```http
GET    /categories
```

### Cart

```http
GET    /cart
POST   /cart/items
PUT    /cart/items/{productId}
DELETE /cart/items/{productId}
```

### Favorites

```http
GET    /favorites
POST   /favorites/{productId}
DELETE /favorites/{productId}
```

## Getting Started

1. Create a PostgreSQL database.
2. Update the database configuration in `application.properties`.
3. Run the application:

```bash
./gradlew bootRun
```

The application will be available at:

```
http://localhost:8081
```

## Future Improvements

* Address Management
* Order Management
* Swagger / OpenAPI Documentation
* Docker Support
* Unit & Integration Tests
