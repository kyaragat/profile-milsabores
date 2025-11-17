# Mil Sabores Profile API

A Spring Boot REST API for user management with JWT authentication, designed for the Mil Sabores application.

## 🚀 Features

- **User Registration** - Create new user accounts
- **User Authentication** - JWT-based login system
- **User Management** - List all users (protected endpoint)
- **Security** - Spring Security with JWT token validation
- **Database Ready** - Configured for both H2 (development) and MySQL (production)

## 🛠 Technologies Used

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **JWT (JJWT)** - JSON Web Token implementation
- **Lombok** - Reduced boilerplate code
- **H2 Database** - Development database
- **MySQL Driver** - Production database connectivity
- **Maven** - Dependency management

## 📋 Prerequisites

Before running this application, ensure you have:

- Java 17 or higher
- Maven 3.6+
- (Optional) MySQL 8.0+ for production

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/profile.git
cd profile
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔧 Configuration

### Development (H2 Database)
The application uses H2 in-memory database by default. Access the H2 console at:
```
http://localhost:8080/h2-console
```
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

### Production (MySQL)
Update `application.properties` for MySQL:

```properties
spring.datasource.url=jdbc:mysql://your-aws-rds-endpoint:3306/milsabores
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
```

## 📚 API Documentation

### Authentication Endpoints

#### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "yourpassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

### Protected Endpoints

#### Get All Users
```http
GET /api/users
Authorization: Bearer your-jwt-token
```

## 🔐 Security

### JWT Authentication Flow

1. **Registration/Login**: User receives JWT token
2. **Protected Requests**: Include token in Authorization header
3. **Token Validation**: Spring Security validates token for each request

### Security Features

- Password encryption using BCrypt
- JWT token expiration (10 hours)
- Stateless authentication
- CORS and CSRF protection

### Debug Information

The application includes security debug prints that show:
- JWT token generation
- Token validation process
- User authentication details

Example console output:
```
=== JWT SECURITY DEBUG ===
JWT generated for user: user@example.com
JWT Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
JWT Expiration: 2024-01-01T12:00:00.000Z
=== END SECURITY DEBUG ===
```

## 🗄 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## 🧪 Testing the API

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@milsabores.com",
    "password": "password123",
    "firstName": "Juan",
    "lastName": "Perez"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@milsabores.com",
    "password": "password123"
  }'
```

### 3. Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## 📁 Project Structure

```
src/main/java/com/milsabores/profile/
├── config/           # Security configuration
├── controller/       # REST controllers
├── dto/             # Data transfer objects
├── entity/          # JPA entities
├── repository/      # Data access layer
├── security/        # JWT utilities and filters
└── service/         # Business logic
```

## 🚀 Deployment

### AWS RDS Setup
1. Create MySQL instance in AWS RDS
2. Update database configuration in `application.properties`
3. Deploy JAR to EC2 instance or Elastic Beanstalk

### Build for Production
```bash
mvn clean package -DskipTests
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support, email desarrollo@milsabores.com or create an issue in the repository.

## 🔄 Version History

- **v1.0.0** (Current)
  - Initial release
  - User registration and authentication
  - JWT security implementation
  - User management endpoints

---

**Mil Sabores** - *Sabor que une*