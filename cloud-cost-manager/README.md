# Cloud Cost Manager - Backend (Java Spring Boot)

This is the Java Spring Boot backend for the Cloud Cost Manager system - a comprehensive cost optimization and monitoring platform for cloud infrastructure.

## Project Structure

```
backend/
├── src/main/java/com/cloud/
│   ├── CloudCostManagerApplication.java    # Main Spring Boot Application
│   ├── config/
│   │   └── DataInitializer.java           # Initialize seed data
│   ├── controller/                         # REST API Controllers
│   │   ├── ServerController.java
│   │   ├── BudgetController.java
│   │   ├── BillingController.java
│   │   ├── ScheduleController.java
│   │   └── ActionLogController.java
│   ├── dto/                               # Data Transfer Objects
│   │   ├── ServerDTO.java
│   │   ├── BudgetDTO.java
│   │   ├── BillingDTO.java
│   │   ├── ScheduleDTO.java
│   │   └── ActionLogDTO.java
│   ├── model/                             # JPA Entities
│   │   ├── Server.java
│   │   ├── Budget.java
│   │   ├── Billing.java
│   │   ├── Schedule.java
│   │   └── ActionLog.java
│   ├── repository/                        # Spring Data JPA Repositories
│   │   ├── ServerRepository.java
│   │   ├── BudgetRepository.java
│   │   ├── BillingRepository.java
│   │   ├── ScheduleRepository.java
│   │   └── ActionLogRepository.java
│   └── service/                           # Business Logic Services
│       ├── ServerService.java
│       ├── BudgetService.java
│       ├── BillingService.java
│       ├── ScheduleService.java
│       └── ActionLogService.java
└── pom.xml                                # Maven Dependencies
```

## Features

### 1. Server Management
- List all cloud servers
- Get server details by ID
- Add new servers
- Update server configuration
- Delete servers
- Filter servers by status/region

### 2. Budget Management
- Track total budget and current spending
- Monitor budget percentage
- Update budget limits
- Set alert thresholds

### 3. Billing History
- View monthly billing records
- Track spending and savings
- Analyze billing trends

### 4. Cost Saving Schedule
- Configure saving mode hours
- Configure performance mode hours
- Enable/disable scheduling

### 5. Action Logs
- Track all system actions and alerts
- View alert history
- Get latest logs with pagination

## Technology Stack

- **Framework**: Spring Boot 3.2.4
- **Language**: Java 21
- **Database**: H2 (Development), MySQL (Production)
- **ORM**: Spring Data JPA + Hibernate
- **Build Tool**: Maven
- **API Architecture**: RESTful
- **CORS**: Enabled for React frontend

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL (optional, for production)

## Installation & Setup

### 1. Build the Project

```bash
cd backend
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Access API Endpoints

Base URL: `http://localhost:8080/api`

#### Server Endpoints
- `GET /api/servers` - Get all servers
- `GET /api/servers/{id}` - Get server by ID
- `POST /api/servers` - Create new server
- `PUT /api/servers/{id}` - Update server
- `DELETE /api/servers/{id}` - Delete server
- `GET /api/servers/status/{status}` - Filter by status

#### Budget Endpoints
- `GET /api/budget` - Get current budget
- `POST /api/budget` - Update budget total
- `PUT /api/budget/spent` - Update spent amount

#### Billing Endpoints
- `GET /api/billing` - Get all billing records
- `GET /api/billing/{id}` - Get billing record by ID
- `POST /api/billing` - Create billing record

#### Schedule Endpoints
- `GET /api/schedule` - Get current schedule
- `PUT /api/schedule` - Update schedule

#### Log Endpoints
- `GET /api/logs` - Get all logs
- `GET /api/logs/latest` - Get latest logs (limit parameter)
- `POST /api/logs` - Add new log entry

## Configuration

Edit `src/main/resources/application.yml` to customize:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:clouddb  # Change for MySQL
    username: sa
    password:
```

## Data Models

### Server
- id (Long)
- name (String)
- type (String) - Web, API, DB, Cache, Worker, ML
- region (String)
- status (String) - running, stopped, saving
- mode (String) - performance, saving
- cpu (Double)
- memory (Double)
- cost (Double)
- uptime (String)
- isProtected (Boolean)

### Budget
- id (Long)
- total (Double)
- spent (Double)
- alertThreshold (Integer) - 80%
- killThreshold (Integer) - 100%
- strategy (String) - balanced, aggressive, conservative

### Billing
- id (Long)
- month (String)
- spent (Double)
- budget (Double)
- savings (Double)

### Schedule
- id (Long)
- savingHour (Integer)
- performanceHour (Integer)
- enabled (Boolean)

### ActionLog
- id (Long)
- type (String) - ok, info, warn, error
- message (String)
- timestamp (String)

## CORS Configuration

The application is configured to accept requests from:
- `http://localhost:5173` (React Vite Dev Server)
- `http://localhost:3000` (Alternative)

## Database

### Development (H2)
- Auto-initializes with seed data
- In-memory database
- Console available at `/api/h2-console`

### Production (MySQL)
Update `application.yml` to use MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cloudcostdb
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

## API Response Format

All endpoints return JSON responses with standard structure:

**Success Response:**
```json
{
  "id": 1,
  "name": "prod-web-01",
  "type": "Web",
  ...
}
```

**Error Response:**
```json
{
  "timestamp": "2024-04-29T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Server not found"
}
```

## Running Tests

```bash
mvn test
```

## Deployment

### Docker
```dockerfile
FROM openjdk:21-slim
COPY target/cloud-cost-manager-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build Docker Image
```bash
mvn clean package
docker build -t cloud-cost-manager:1.0.0 .
docker run -p 8080:8080 cloud-cost-manager:1.0.0
```

## Integration with React Frontend

Update the frontend API base URL in `src/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';

const api = {
  async getServers() {
    const response = await axios.get(`${API_BASE_URL}/servers`);
    return response.data;
  },
  // ... other methods
};
```

## Troubleshooting

### Port Already in Use
```bash
# Change port in application.yml
server.port: 8081
```

### Database Connection Issues
- Ensure H2 driver is in classpath (or MySQL driver for production)
- Check database URL in `application.yml`

### CORS Issues
- Verify frontend is running on allowed origin
- Check `CloudCostManagerApplication.cors()` configuration

## License

MIT

## Support

For issues or questions, contact the development team.
