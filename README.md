# Journey API Testing

## Overview
This project tests the `POST /api/journeys` and `GET /api/journeys/:journey_id` endpoints for the Splytech QA interview task. It includes manual and automated testing using Cucumber, JUnit, and RestAssured.

## Task Answers

### **1. Found Bugs and Issues**
During testing, the following issues were identified:

- **Invalid Input Handling:**
    - When `phone_number` or `longitude` is missing, the API did not return a `400 Bad Request` error. It returns `500 Internal Server` error with `INTERNAL_SERVER_ERROR` instead.
    - When an invalid `latitude` or `longitude` is provided (e.g., beyond valid range), the API still returns `200 OK`. The following pairs of values should return a `400 Bad Request` error.
  
    | Test Case | Latitude | Longitude | Expected Response |
    |-----------|---------|----------|------------------|
    | **Invalid Latitude - Above Max** | `91` | `-0.15` | `400 Bad Request` |
    | **Invalid Latitude - Below Min** | `-91` | `-0.15` | `400 Bad Request` |
    | **Invalid Longitude - Above Max** | `51.5` | `181` | `400 Bad Request` |
    | **Invalid Longitude - Below Min** | `51.5` | `-181` | `400 Bad Request` |

- **Data Validation Issues:**
    - API returns `200 OK` response when wrong format of `phone_number` is given (e.g. +447594855513sss). It should be validated properly without accepting non-numeric or incorrectly formatted numbers.
    - `departure_date` should follow ISO 8601 format. It accepts random strings (e.g. 2019-01-23T13:24:35.703Zsssss) , validation is weak. It should return a `400 Bad Request` error.
    - `name` and `surname` should follow alphabetic format. Both parameters accept numeric characters (e.g. John123), validation is weak. It should return a `400 Bad Request` error.

- **Error Response Consistency:**
    - If an invalid `journey_id` is provided in `GET /api/journeys/:journey_id`, the API returns `204 No Content`. It should return a `404 Not Found` error with a meaningful message.

### **2. Additional Testing Considerations**
- **Performance Testing:**
    - Stress test the `POST` endpoint with a high volume of requests.
    - Load test `GET` requests to see if response times degrade under heavy usage.

- **Security Testing:**
    - Ensure API does not accept SQL injections or script injections.
    - Check for authentication/authorization issues (if applicable).

- **Boundary Testing:**
    - Test extreme values for latitude/longitude.
    - Check edge cases for string inputs (empty, special characters, etc.).

### **3. Ensuring Testing Quality**
- **Automation:**
    - Using Cucumber and RestAssured for API automation ensures repeatability.
    - Running automated tests in a CI/CD pipeline helps detect issues early.

- **Test Case Coverage:**
    - Include both positive and negative test cases.
    - Review API specifications and add test scenarios accordingly.

- **Logging & Reporting:**
    - Use detailed logging to track failures.
    - Generate reports for better debugging.

### **4. Challenges Faced**
- **Limited API Documentation:**
    - Assumptions had to be made regarding error handling and response structure.

- **Data Cleanup:**
    - Since new journeys are created with each test, old test data should be removed if there's a database cleanup mechanism.

- **Automation Challenges:**
    - Handling dynamic journey IDs required careful test design.

### **5. Improvements for the Tech Task**
- **Provide Sample Error Responses**
    - This helps testers understand expected failure cases.

- **Clarify Validation Rules**
    - Example: What is the expected phone number format? Should `surname` be optional?

- **Include Authentication (If Needed)**
    - If the real API requires authentication, instructions should be provided.

## Automated Testing Setup

### **Prerequisites**
- Install **Java 23.0.2**
- Install **Apache Maven 3.9.9**
- Ensure an internet connection to fetch dependencies.

## How to Run the Tests 🚀

1. Clone the repository into local
2. Navigate to the project directory and execute:
   ```sh
   mvn clean test -Dtest=runners.TestRunner
   ```
4. Generate the Cucumber HTML report:
   ```sh
   mvn verify
   ```
5. View the report:
   ```sh
   open target/cucumber-html-reports/index.html
   ```

