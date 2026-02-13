
**E-Commerce Application**
An E-Commerce application with features:
-User registration
-Product and category management
-Cart creation and Add-to-cart function
-Order placement with currency conversion via Frankfurter API

**Techs Used**
Java
Spring Boot
JPA for DB
MySQL
Swagger for API
Frankfurther for currency conversion


**Entity Relation**

![img.png](img.png)

**Foldr Structure**

src/main/java/com/shrijal/e_commerce/\

├── controller/(REST Controllers)
│   ├── UserController.java
│   ├── CategoryController.java
│   ├── ProductController.java
│   ├── CartController.java
│   └── OrderController.java
├── service/  (Business Logic Layer)
│   ├── UserService.java
│   ├── CategoryService.java
│   ├── ProductService.java
│   ├── CartService.java
│   └── OrderService.java
├── repository/   (Data Layer)
│   ├── UserRepository.java
│   ├── CategoryRepository.java
│   ├── ProductRepository.java
│   ├── CartRepository.java
│   ├── CartItemRepository.java
│   ├── OrderRepository.java
│   └── OrderItemRepository.java
├── model/    (Entity Classes)
│   ├── User.java
│   ├── Category.java
│   ├── Product.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderItem.java
├── dto/
│   ├── request/   (Request DTOs)
│   │   ├── UserRequestDTO.java
│   │   ├── CategoryRequestDTO.java
│   │   ├── ProductRequestDTO.java
│   │   ├── CartRequestDTO.java
│   │   └── OrderRequestDTO.java
│   └── response/  (Response DTOs)
│       ├── UserResponseDTO.java
│       ├── CategoryResponseDTO.java
│       ├── ProductResponseDTO.java
│       ├── CartResponseDTO.java
│       ├── CartItemResponseDTO.java
│       ├── OrderResponseDTO.java
│       └── OrderItemResponseDTO.java
├── exception/    (Exception handling)
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
├
├── config/(Application Configuration)
│   ├── AppConfig.java
└── ECommerceApplication.java  (Main Application Class)

**Key Features**

1. User Registration
-Users are registered using the

2. Cart
- Each user has one cart created automatically upon registration
- Cart items store the price at the time of addition (preserves pricing)
- Supports adding, viewing, and removing items

3. Order Placement
- Orders are created from cart items
- Cart is automatically cleared after successful order placement
- Order items preserve historical pricing

4. Currency Conversion
- Uses Frankfurter API (https://www.frankfurter.app/)
- Converts INR to any supported currency like USD, EUR
- Includes error handling for invalid currencies
- Stores both original (INR) and converted amounts

5. The application includes error handling for:
- Invalid user/product/category IDs
- Empty cart during checkout
- Currency conversion failures
- Duplicate email registration
- Invalid input validation

6. Swagger
- Using swagger(http://localhost:8080/swagger-ui/index.html) for testing API

![img_1.png](img_1.png)
![img_2.png](img_2.png)



