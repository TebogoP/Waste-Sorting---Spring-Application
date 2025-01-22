# Waste Sorting Application

## Overview
The **Waste Sorting Application** is a Spring Boot-based backend system developed for Enviro365 to promote sustainable waste management practices. It allows users to interact with APIs for managing waste categories, disposal guidelines, and recycling tips. This application is designed to support a mobile frontend, enabling users to make environmentally conscious decisions.

---

## Features
1. **Waste Categories**:
   - Add, update, delete, and retrieve waste categories.
   - JSON-formatted responses with structured data.

2. **Disposal Guidelines**:
   - Manage guidelines for proper waste disposal.
   - APIs to create, retrieve, and delete guidelines.

3. **Recycling Tips**:
   - CRUD operations for recycling tips.

4. **Validation**:
   - Input validation using Spring Boot annotations to ensure data integrity.

5. **Centralized Error Handling**:
   - Custom exception handlers to provide meaningful error messages.

---

## Technologies Used
1. **Backend**:
   - Java 21
   - Spring Boot 3.2.0
   - Spring Data JPA
   - Spring Validation

2. **Database**:
   - H2 In-Memory Database

3. **API Documentation**:
   - SpringDoc OpenAPI (Swagger)

4. **Build Tool**:
   - Maven

---

## Prerequisites
- **Java Development Kit (JDK) 21**
- **Maven 3.8+**

---

## Installation and Running the Application

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/waste-sort.git
cd waste-sort
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```
The application will start on [http://localhost:8080](http://localhost:8080) by default. If the port is changed, adjust the URL accordingly.

---

## API Endpoints

### Waste Categories
#### 1. **Create a Waste Category**
- **POST** `/api/waste-categories`
- **Request Body**:
  ```json
  {
    "name": "Plastic Waste",
    "description": "Waste that includes recyclable plastics."
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "name": "Plastic Waste",
    "description": "Waste that includes recyclable plastics."
  }
  ```

#### 2. **Retrieve All Categories**
- **GET** `/api/waste-categories`
- **Response**:
  ```json
  [
    {
      "id": 1,
      "name": "Plastic Waste",
      "description": "Waste that includes recyclable plastics."
    }
  ]
  ```

#### 3. **Retrieve a Category by ID**
- **GET** `/api/waste-categories/{id}`
- **Response**:
  ```json
  {
    "id": 1,
    "name": "Plastic Waste",
    "description": "Waste that includes recyclable plastics."
  }
  ```

#### 4. **Update a Category**
- **PUT** `/api/waste-categories/{id}`
- **Request Body**:
  ```json
  {
    "name": "Updated Waste",
    "description": "Updated description."
  }
  ```

#### 5. **Delete a Category**
- **DELETE** `/api/waste-categories/{id}`

### Disposal Guidelines
#### 1. **Create a Disposal Guideline**
- **POST** `/api/disposal-guidelines`
- **Request Body**:
  ```json
  {
    "guideline": "Dispose of plastics in the recycling bin.",
    "wasteType": "Plastic"
  }
  ```

#### 2. **Retrieve All Guidelines**
- **GET** `/api/disposal-guidelines`

#### 3. **Delete a Guideline**
- **DELETE** `/api/disposal-guidelines/{id}`

### Recycling Tips
#### 1. **Create a Recycling Tip**
- **POST** `/api/recycling-tips`
- **Request Body**:
  ```json
  {
    "tip": "Crush plastic bottles before recycling to save space.",
    "category": "Plastic"
  }
  ```

#### 2. **Retrieve All Tips**
- **GET** `/api/recycling-tips`

#### 3. **Delete a Tip**
- **DELETE** `/api/recycling-tips/{id}`

---

## Validation Rules
- **WasteCategory**:
  - `name`: Cannot be blank.
  - `description`: Cannot be blank.
- **DisposalGuideline**:
  - `guideline`: Cannot be blank.
  - `wasteType`: Cannot be blank.
- **RecyclingTip**:
  - `tip`: Cannot be blank.
  - `category`: Cannot be blank.

---

## Error Handling
The application provides detailed error responses for invalid requests. Example:

- **Validation Error**:
  ```json
  {
    "timestamp": "2025-01-22T12:34:56",
    "status": 400,
    "error": "Bad Request",
    "message": "Name is required",
    "path": "/api/waste-categories"
  }
  ```

---

## Contact Information
For any questions or support, please contact:

- **Name**: Ayubu Lesego Boase
- **Email**: ayubu.boase@example.com
- **GitHub**: [https://github.com/ayubu-lesego](https://github.com/ayubu-lesego)

---

## License
This project is licensed under the [MIT License](LICENSE).
