# Banking Application

This is a sample README for a hypothetical banking application project. It provides a brief overview of the project, its components, and how to set it up.

## Overview

The Banking Application is a Spring-based Java application that provides services for managing bank account holders. It includes a RESTful API for creating, retrieving, and deactivating account holders. The application also handles the storage of account holder information in files.

## Project Structure

- **`com.bankingapp.controller`**: Contains the Spring controllers that define the REST endpoints for managing account holders.
- **`com.bankingapp.model`**: Defines the data models used in the application, including `AccountHolder` and `AccountHolderDTO`.
- **`com.bankingapp.repository`**: Provides the repository interface for working with account holder data.
- **`com.bankingapp.service`**: Contains the service classes responsible for business logic, such as creating, retrieving, and deactivating account holders. It also includes file-related services for writing and deactivating account holders in files.

## Controllers

- `AccountHolderController`: Defines REST endpoints for managing account holders, including creating, retrieving, and deactivating them.

## Services

- `AccountHolderServiceImpl`: Implements the business logic for creating, retrieving, and deactivating account holders. It interacts with the `AccountHolderRepository` and `AccountHolderFileService` to perform these actions.

- `AccountHolderFileServiceImpl`: Manages writing account holder information to files and deactivating account holders in files.

## Repository

- `AccountHolderRepository`: Provides data access methods for working with account holder entities.

## How to Set Up

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/dominiccdj/BankingApp.git
   ```

2. Open the project in your favorite Java development environment (e.g., IntelliJ IDEA, Eclipse).

3. Run the application as a Spring Boot application.

4. Access the API via the defined endpoints, e.g., `http://localhost:8080/account-holder`.

## API Endpoints

- `GET /account-holder`: Retrieve a list of all active account holders.
- `GET /account-holder/{oib}`: Retrieve an active account holder by their OIB.
- `PUT /account-holder`: Create a new account holder and create an acount holder file for that account in project root.
- `DELETE /account-holder/{oib}`: Deactivate an account holder by their OIB and deactivate their acount holder file.

## Testing

The project includes unit tests for the services and controllers. You can run the tests to ensure that the application functions correctly.

To run the tests, use your favorite Java testing framework (e.g., JUnit) or your development environment's built-in testing tools.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Authors

- Dominik Jurkovic @dominiccdj

This Readme file was created with some help from AI, but the code was written by me. :) 
