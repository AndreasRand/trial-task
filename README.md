# Trial-task - Delivery Fee Calculator

## Overview
This project implements a delivery fee calculator for a food delivery application. The calculator considers regional base fees, vehicle types, and weather conditions to determine the total delivery fee. It provides a RESTful interface for requesting delivery fees based on input parameters and business rule management.

## Features

- **CRUD operations for business rules**: Allows users to use CRUD operations through REST endpoints to manage different business rules.
- **Delivery Fee calculation**: Allows users to calculate delivery fee based on request input.
- **CronJob for Importing Weather Data**: Imports weather data from the Estonian Environment Agency's weather portal.

## Technologies Used

- **Java 17**: Backend programming language.
- **Spring Boot**: Framework for building RESTful APIs.
- **JUnit 5**: Testing framework for unit and integration tests.
- **MockMvc**: Mocking framework for testing Spring MVC controllers.
- **H2 Database**: In-memory database for storing ATEF configurations during tests.
- **Liquibase**: Library for parsing JSON responses in tests.
- **Jakarta XML Binding**: XML framework to automate mapping between XML documents and Java objects.

## Setup

1. **Clone the Repository**: Clone the repository to your local machine.
   ```bash
   git clone <repository_url>
   ```

2. **Build the Project**: Navigate to the project directory and build the project using Maven.
   ```bash
   mvn clean install
   ```

3. **Run Tests**: Execute the test suite to ensure everything is working correctly.
   ```bash
   mvn test
   ```

4. **Run the Application**: Start the application locally.
   ```bash
   mvn spring-boot:run
   ```

5. **Explore Endpoints**: Once the application is running, you can explore the API endpoints using tools like Postman or cURL.

## API Endpoints

### Air Temperature Extra Fee (ATEF)

#### Create ATEF Configuration

- **URL**: `/api/ATEF`
- **Method**: `POST`
- **Request Body**:
  ```json5
  {
    "vehicleType": "SCOOTER", // String: Vehicle type (Possible values: "CAR", "SCOOTER", "BIKE")
    "conditionType": "FIRST_DEGREE", // String: Condition type (Possible values: "FIRST_DEGREE", "SECOND_DEGREE", "DISALLOWED"), condition type is for grouping all eligible bonus types for the vehicle
    "minTempRequirement": -20.0, // Float: Minimum temperature requirement (in Celsius), can be null
    "maxTempRequirement": -10, // Float: Maximum temperature requirement (in Celsius)
    "amount": 0.7 // Float: Amount of extra fee (in EUR)
  }
  ```
- **Description**: Creates a new ATEF configuration for the specified vehicle type, condition type, temperature range and amount.

#### Retrieve ATEF Configuration

- **URL**: `/api/ATEF/{id}`
- **Method**: `GET`
- **Path Variable**: `id` (ID of the ATEF configuration)
- **Description**: Retrieves details of the ATEF configuration with the specified ID.

#### Update ATEF Configuration

- **URL**: `/api/ATEF/{id}`
- **Method**: `PUT`
- **Path Variable**: `id` (ID of the ATEF configuration)
- **Request Body**: Same as the request body for creating a configuration.
- **Description**: Updates the ATEF configuration with the specified ID.

#### Delete ATEF Configuration

- **URL**: `/api/ATEF/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` (ID of the ATEF configuration)
- **Description**: Deletes the ATEF configuration with the specified ID.

### Wind Speed Extra Fee (WSEF)

#### Create WSEF Configuration

- **URL**: `/api/WSEF`
- **Method**: `POST`
- **Request Body**:
  ```json5
  {
    "vehicleType": "SCOOTER", // String: Vehicle type (Possible values: "CAR", "SCOOTER", "BIKE")
    "conditionType": "FIRST_DEGREE", // String: Condition type (Possible values: "FIRST_DEGREE", "SECOND_DEGREE", "DISALLOWED"), condition type is for grouping all eligible bonus types for the vehicle
    "minWindSpeedRequirement": -20.0, // Float: Minimum wind speed requirement (in Celsius)
    "maxWindSpeedRequirement": -10, // Float: Maximum wind speed requirement (in Celsius), can be null
    "amount": 0.7 // Float: Amount of extra fee (in EUR)
  }
  ```
- **Description**: Creates a new WSEF configuration for the specified vehicle type, condition type, temperature range and amount.

#### Retrieve WSEF Configuration

- **URL**: `/api/WSEF/{id}`
- **Method**: `GET`
- **Path Variable**: `id` (ID of the WSEF configuration)
- **Description**: Retrieves details of the WSEF configuration with the specified ID.

#### Update WSEF Configuration

- **URL**: `/api/WSEF/{id}`
- **Method**: `PUT`
- **Path Variable**: `id` (ID of the WSEF configuration)
- **Request Body**: Same as the request body for creating a configuration.
- **Description**: Updates the WSEF configuration with the specified ID.

#### Delete WSEF Configuration

- **URL**: `/api/WSEF/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` (ID of the WSEF configuration)
- **Description**: Deletes the WSEF configuration with the specified ID.

### Weather Phenomenon Extra Fee (WPEF)

#### Create WPEF Configuration

- **URL**: `/api/WPEF`
- **Method**: `POST`
- **Request Body**:
  ```json5
  {
    "vehicleType": "SCOOTER", // String: Vehicle type (Possible values: "CAR", "SCOOTER", "BIKE")
    "conditionType": "FIRST_DEGREE", // String: Condition type (Possible values: "FIRST_DEGREE", "SECOND_DEGREE", "DISALLOWED"), condition type is for grouping all eligible bonus types for the vehicle
    "phenomenonPattern": "(.*)(rain)(.*)", // String: Regex pattern to match the phenomenon (in lowercase)
    "amount": 0.7 // Float: Amount of extra fee (in EUR)
  }
  ```
- **Description**: Creates a new WPEF configuration for the specified vehicle type, condition type, phenomenon pattern and amount.

#### Retrieve WPEF Configuration

- **URL**: `/api/WPEF/{id}`
- **Method**: `GET`
- **Path Variable**: `id` (ID of the WPEF configuration)
- **Description**: Retrieves details of the WPEF configuration with the specified ID.

#### Update WPEF Configuration

- **URL**: `/api/WPEF/{id}`
- **Method**: `PUT`
- **Path Variable**: `id` (ID of the WPEF configuration)
- **Request Body**: Same as the request body for creating a configuration.
- **Description**: Updates the WPEF configuration with the specified ID.

#### Delete WPEF Configuration

- **URL**: `/api/WPEF/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` (ID of the WPEF configuration)
- **Description**: Deletes the WPEF configuration with the specified ID.

### Regional Base Fee (RBF)

#### Create RBF Configuration

- **URL**: `/api/RBF`
- **Method**: `POST`
- **Request Body**:
  ```json5
  {
    "vehicleType": "SCOOTER", // String: Vehicle type (Possible values: "CAR", "SCOOTER", "BIKE")
    "cityType": "TALLINN", // String: City type (Possible values: "TALLINN", "TARTU", "PÄRNU")
    "amount": 0.7 // Float: Amount of extra fee (in EUR)
  }
  ```
- **Description**: Creates a new RBF configuration for the specified vehicle type, city type and amount.

#### Retrieve RBF Configuration

- **URL**: `/api/RBF/{id}`
- **Method**: `GET`
- **Path Variable**: `id` (ID of the RBF configuration)
- **Description**: Retrieves details of the RBF configuration with the specified ID.

#### Update RBF Configuration

- **URL**: `/api/RBF/{id}`
- **Method**: `PUT`
- **Path Variable**: `id` (ID of the RBF configuration)
- **Request Body**: Same as the request body for creating a configuration.
- **Description**: Updates the RBF configuration with the specified ID.

#### Delete RBF Configuration

- **URL**: `/api/RBF/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` (ID of the RBF configuration)
- **Description**: Deletes the RBF configuration with the specified ID.

### Delivery Fee calculation

#### Retrieve Delivery Fee based on input

- **URL**: `/api/calculateFee`
- **Method**: `POST`
- **Request Body**:
```json5
{
  "vehicleType": "SCOOTER", // String: Vehicle type (Possible values: "CAR", "SCOOTER", "BIKE")
  "cityType": "TALLINN", // String: City type (Possible values: "TALLINN", "TARTU", "PÄRNU")
  "timestamp": 1710975712 // Integer: Timestamp for the required data, set to null for the latest
}
```
- **Description**: Retrieves the total delivery fee for the vehicle type, city type and timestamp. If timestamp is set to null, returns the latest.

## Database Schema Structure

### air_temperature_extra_fees Table

| Column Name                  | Data Type | Constraints          |
|------------------------------|-----------|----------------------|
| id                           | bigint    | Primary Key, Auto Increment |
| vehicle_type                 | tinyint   | Not Null             |
| condition_type               | tinyint   | Not Null             |
| min_temperature_requirement  | float     |                      |
| max_temperature_requirement  | float     | Not Null             |
| amount                       | float     | Not Null             |
| timestamp                    | bigint    | Not Null             |

### regional_base_fees Table

| Column Name                  | Data Type | Constraints          |
|------------------------------|-----------|----------------------|
| id                           | bigint    | Primary Key, Auto Increment |
| city_type                    | tinyint   | Not Null             |
| vehicle_type                 | tinyint   | Not Null             |
| amount                       | float     | Not Null             |
| timestamp                    | bigint    | Not Null             |

### weather_data Table

| Column Name    | Data Type | Constraints          |
|----------------|-----------|----------------------|
| id             | bigint    | Primary Key, Auto Increment |
| station_name   | varchar(255) | Not Null             |
| wmo_code       | bigint    | Not Null             |
| air_temperature| float     | Not Null             |
| wind_speed     | float     | Not Null             |
| phenomenon     | varchar(255) |                      |
| timestamp      | bigint    | Not Null             |

### weather_phenomenon_extra_fees Table

| Column Name                  | Data Type | Constraints          |
|------------------------------|-----------|----------------------|
| id                           | bigint    | Primary Key, Auto Increment |
| vehicle_type                 | tinyint   | Not Null             |
| condition_type               | tinyint   | Not Null             |
| phenomenon_pattern           | varchar(255) | Not Null             |
| amount                       | float     | Not Null             |
| timestamp                    | bigint    | Not Null             |

### wind_speed_extra_fees Table

| Column Name                  | Data Type | Constraints          |
|------------------------------|-----------|----------------------|
| id                           | bigint    | Primary Key, Auto Increment |
| vehicle_type                 | tinyint   | Not Null             |
| condition_type               | tinyint   | Not Null             |
| min_wind_speed_requirement   | float     | Not Null             |
| max_wind_speed_requirement   | float     |                      |
| amount                       | float     | Not Null             |
| timestamp                    | bigint    | Not Null             |

## Testing

The API comes with a test suite to ensure the correctness of its functionality. You can run the tests using Maven:

```bash
mvn test
```

## Author
Andreas Randmäe / AndreasR
