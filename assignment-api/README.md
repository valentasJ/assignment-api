---

# API Assignment Project

This is an API assignment project implemented with **Spring Boot**. It demonstrates how to create and manage companies and owners with functionality to handle sensitive data, role-based access control, and modular business logic.

## Features

- **Spring Boot Application** for backend services.
- **Sensitive Data Filtering**: Sensitive data like `socialSecurityNumber` is filtered based on user roles.
- **Transactional Service Layer** to ensure business logic integrity.
- **CRUD Operations**: Create, Retrieve, Update, and Delete functionality for companies and their owners.
- **Role-based Access Control**: Non-admin users have restricted access to sensitive fields.
- **Modular Design**: Services and logic are organized within the following modules:
    - **core**: Contains shared logic (e.g., `SensitiveDataHandler`).
    - **owner**: Manages owner-related operations.
    - **company**: Manages company-related operations.
    - **application**: Contains the main application class and configuration to run the project.

## Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17+** (OpenJDK or Oracle JDK)
- **Maven** (for dependency management and building the project)
- **Docker** (for containerized deployment)
- **Docker Compose** (for managing multi-container applications)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/api-assignment.git
cd api-assignment/application
```

### 2. Build the project

Run the following command to build the project and resolve dependencies:

```bash
mvn clean install
```

This will install all the necessary dependencies for all modules (`core`, `owner`, `company`, `application`).

### 3. Run the Spring Boot Application

To run the Spring Boot application locally, use the following command from the `application` directory:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 4. Running the Application with Docker

#### Step 1: Build the Docker image

To build the Docker image, make sure you are in the `application` directory and then use the following command:

```bash
docker build -t api-assignment .
```

This command will build the Docker image using the `Dockerfile` in the project. It will copy the built JAR file (`api-assignment-0.0.1-SNAPSHOT.jar`) into the container and use `openjdk:11-jre-slim` as the base image to run the application.

#### Step 2: Run the Docker container

After the image is built, you can run the Docker container with the following command:

```bash
docker run -p 8080:8080 api-assignment
```

This will run the container and expose the Spring Boot application on `http://localhost:8080`.

#### Step 3: Running the Application with Docker Compose

Alternatively, you can use Docker Compose to manage the application. Docker Compose is useful when you have multiple services to orchestrate.

1. Create a `docker-compose.yml` file in the `application` directory (if it doesn't exist already) with the following content:

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
```

2. To start the application with Docker Compose, run the following command:

```bash
docker-compose up --build
```

This command will:

- Build the Docker image (if not already built).
- Start the container and expose the application on port `8080`.

The Spring Boot application will be available at `http://localhost:8080`.

### 5. Access the Application

You can interact with the APIs via **`curl`** or **Postman**.

- **Create a company:**
```bash
curl -X POST \
  'http://localhost:8080/api/companies' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Tech Corp",
  "country": "USA",
  "phoneNumber": "+1234567890",
  "owners": [{"name": "John Doe", "socialSecurityNumber": "123-45-6789"}]
}'
```

- **Get all companies:**
```bash
curl -X GET 'http://localhost:8080/api/companies'
```

- **Get a specific company by ID:**
```bash
curl -X GET 'http://localhost:8080/api/companies/{id}'
```

### 6. Sensitive Data Handling

The `SensitiveDataHandler` class is responsible for filtering sensitive fields. The filtering is based on the role of the authenticated user (Admin vs. Non-admin).

- **Non-admin users**: They will not see sensitive fields like `socialSecurityNumber`, which will be replaced with `null` in the response.
- **Admin users**: They can view sensitive data like `socialSecurityNumber`.

### 7. Authentication and Roles

The application supports **basic authentication** with two roles:

- **Admin**: Username: `admin`, Password: `admin`
- **User**: Username: `user`, Password: `user`

To authenticate, you must include the appropriate credentials in the request header, like so:

```bash
curl -u admin:admin -X GET 'http://localhost:8080/api/companies'
```

or for a user:

```bash
curl -u user:user -X GET 'http://localhost:8080/api/companies'
```

Based on the role, the response will vary for sensitive fields like `socialSecurityNumber`.

## API Endpoints

### Companies API

- **GET** `/api/companies`: Get all companies.
- **GET** `/api/companies/{id}`: Get a specific company by ID.
- **POST** `/api/companies`: Create a new company.

### Owners API

- **GET** `/api/owners`: Get all owners.
- **GET** `/api/owners/{id}`: Get an owner by ID.
- **POST** `/api/owners`: Create a new owner.

## Project Tree

Here’s a simple project tree for your API Assignment project:

```
api-assignment/
├── core/
│   ├── SensitiveDataHandler.java
│   └── ... (other shared logic files)
├── owner/
│   ├── OwnerService.java
│   ├── OwnerController.java
│   └── ... (other owner-related files)
├── company/
│   ├── CompanyService.java
│   ├── CompanyController.java
│   └── ... (other company-related files)
├── application/
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── Application.java (Spring Boot main class)
│   ├── application.properties (configuration file)
│   └── pom.xml (Maven dependencies)
└── LICENSE
```

This tree shows the structure with key files and modules:

- **`core/`**: Contains shared logic, e.g., `SensitiveDataHandler`.
- **`owner/`**: Contains owner management files.
- **`company/`**: Contains company management files.
- **`application/`**: Contains the main application setup, Docker configurations, and dependencies.

You can expand each folder as per your project’s requirements with more files for services, controllers, entities, etc.

## Modules Explanation

The project is structured into the following four modules:

- **`core`**: Contains shared logic and services, such as `SensitiveDataHandler`.
- **`owner`**: Contains logic and services for managing owners.
- **`company`**: Contains logic and services for managing companies.
- **`application`**: Contains the main Spring Boot application and is responsible for running the project. All running commands should be executed from this module.

## Running Tests

You can run the tests with the following command from the `application` directory:

```bash
mvn test
```

## Contributing

We welcome contributions! If you'd like to contribute, please follow these steps:

1. Fork the repository
2. Create a new branch for your changes
3. Make your changes and write tests if necessary
4. Run tests to ensure everything is working
5. Submit a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Special thanks to God and Sun :) 
---

