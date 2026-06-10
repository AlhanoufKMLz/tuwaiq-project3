# Bank Management System

Spring Boot web application for managing bank users, employees, customers, accounts, and financial transactions.

## Dependencies

- Spring Web
- Lombok
- Validation
- MySQL Driver
- Spring Data JPA
- Spring Security

## Default Admin

The app creates one admin user at startup if it does not exist:

- Username: `admin`
- Password: `admin123`

Use HTTP Basic authentication for protected endpoints.

## Main Endpoints

- `POST /api/v1/auth/register/customer`
- `POST /api/v1/auth/register/employee`
- `GET /api/v1/admin/users/get`
- `GET /api/v1/customers/get`
- `GET /api/v1/employees/get`
- `POST /api/v1/accounts/create/{customerId}`
- `PUT /api/v1/accounts/activate/{accountId}`
- `GET /api/v1/accounts/details/{accountId}`
- `GET /api/v1/accounts/my-accounts`
- `PUT /api/v1/accounts/deposit/{accountId}`
- `PUT /api/v1/accounts/withdraw/{accountId}`
- `PUT /api/v1/accounts/transfer`
- `PUT /api/v1/accounts/block/{accountId}`

## MySQL

Create a database named `bank_management_system`, then update `src/main/resources/application.properties` if your MySQL username or password is different.
